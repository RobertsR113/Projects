package com.example;

import java.io.IOException;

import com.example.Models.Logs;
import com.example.Models.Staff;
import com.example.Storage.LogsManager;
import com.example.Storage.LogsStorage;
import com.example.Storage.StaffManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginScreen
{
    @FXML private Label errorMessage;
    @FXML private TextField email;
    @FXML private PasswordField password;

    private boolean isAdmin = false;
    @FXML protected void onLoginClick() throws IOException
    {
        if (!validInput())
            return;

        Staff staff = StaffManager.manager.authenticate(email.getText().trim());

        StaffManager.manager.setLoggedInStaff(staff);
        LogsManager.manager.addNew(new Logs(staff, "Has Successfully Logged In!"));
        LogsStorage.storage.save();

        FXMLLoader loader;
        int width; int height;
        Stage stage = (Stage) email.getScene().getWindow();
        if (isAdmin)
        {
            loader = new FXMLLoader(getClass().getResource("/com/example/Admin/mainScreen.fxml"));
            stage.setTitle("Admin");
            width = 1200; height = 645;
        }
        else
        {
            loader = new FXMLLoader(getClass().getResource("/com/example/Staff/mainScreen.fxml"));
            stage.setTitle("Info");
            width = 519; height = 316;
        }
        
        Scene scene = new Scene(loader.load(), width, height);
        stage.setScene(scene);
        stage.show();
    }

    private int emptyFieldCount = 0;
    private int invalidEmailCount = 0;
    private int invalidPasswordCount = 0;
    private boolean validInput() throws IOException
    {
        if (email.getText().isEmpty() || password.getText().isEmpty())
        {
            invalidEmailCount = 0; invalidPasswordCount = 0;
            emptyFieldCount++;
            errorMessage.setText("Please Fill All Fields! (" + emptyFieldCount + ")");
            return false;
        }
        
        Staff staff = StaffManager.manager.authenticate(email.getText().trim());

        if (staff == null)
        {
            emptyFieldCount = 0; invalidPasswordCount = 0;
            invalidEmailCount++;
            errorMessage.setText("Invalid Email! (" + invalidEmailCount + ")");
            return false;
        }

        if (!staff.getPerson().getPassword().equals(password.getText()))
        {
            if (invalidPasswordCount == 3)
            {
                errorMessage.setText("Too Many Failed Attempts!");
                LogsManager.manager.addNew(new Logs(staff, "Failed To Login! (Max Attempt Count Reached)!"));
                LogsStorage.storage.save();
                return false;
            }
            emptyFieldCount = 0; invalidEmailCount = 0;
            invalidPasswordCount++;
            errorMessage.setText("Invalid Password! (" + invalidPasswordCount + ")");
            return false;
        }

        if (staff.getDepartment().getName().equals("Administration"))
            isAdmin = true;

        return true;
    }
}
