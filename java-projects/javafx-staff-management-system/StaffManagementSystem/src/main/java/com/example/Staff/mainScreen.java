package com.example.Staff;

import java.io.IOException;

import com.example.Models.Staff;
import com.example.Storage.StaffManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class mainScreen
{
    @FXML private Label departmentName;
    @FXML private Label departmentRole;
    @FXML private Label wage;

    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField password;
    @FXML private TextField email;

    @FXML private Button staffListButton;

    @FXML public void initialize()
    {
        Staff staff = StaffManager.manager.getLoggedInStaff();

        name.setText(staff.getName());
        surname.setText(staff.getSurname());
        password.setText(staff.getPassword());
        email.setText(staff.getEmail());
        departmentName.setText(staff.getDepartmentName());
        departmentRole.setText(staff.getRole());
        wage.setText(staff.getWage());

        if (!staff.getRole().equals("Lead"))
        {
            staffListButton.setDisable(true);
            staffListButton.setVisible(false);
        }
    }

    @FXML protected void onStaffListClick() throws IOException 
    {
        Stage stage = (Stage) email.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Staff/staffList.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 645);
        stage.setTitle("StaffList");
        stage.setScene(scene);
        stage.show();
    }

    @FXML protected void onExitClick() throws IOException 
    {
        Stage stage = (Stage) email.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginScreen.fxml"));
        Scene scene = new Scene(loader.load(), 600, 500);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
