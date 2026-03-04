package com.example.Admin;

import java.io.IOException;
import java.util.UUID;

import com.example.Models.Department;
import com.example.Models.Logs;
import com.example.Models.Person;
import com.example.Models.Staff;
import com.example.Storage.LogsManager;
import com.example.Storage.LogsStorage;
import com.example.Storage.StaffManager;
import com.example.Storage.StaffStorage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class mainScreen
{
    @FXML private Label errorMessage;
    @FXML private Label welcomeMessage;
    @FXML private Label successMessage;

    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField password;
    @FXML private TextField email;
    @FXML private TextField wage;

    @FXML private ChoiceBox<String> departmentChoice;
    @FXML private ChoiceBox<String> roleChoice;

    @FXML private TableView<Staff> staffTable;

    @FXML private TableColumn<Staff, String> colName;
    @FXML private TableColumn<Staff, String> colSurname;
    @FXML private TableColumn<Staff, String> colEmail;
    @FXML private TableColumn<Staff, String> colPassword;
    @FXML private TableColumn<Staff, String> colDepartment;
    @FXML private TableColumn<Staff, String> colRole;
    @FXML private TableColumn<Staff, String> colWage;
    @FXML private TableColumn<Staff, UUID> colUUID;

    private final ObservableList<Staff> staffObservableList = FXCollections.observableArrayList();
    private final Staff currentLoggedInStaff = StaffManager.manager.getLoggedInStaff();

    @FXML public void initialize()
    {
        welcomeMessage.setText("Welcome back " + 
                    currentLoggedInStaff.getPerson().getName() + " " + 
                    currentLoggedInStaff.getPerson().getSurname() + " !");

        staffObservableList.addAll(StaffManager.manager.getStaffList());

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
        colUUID.setCellValueFactory(new PropertyValueFactory<>("uuid"));

        staffTable.setItems(staffObservableList);
        staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        departmentChoice.getItems().addAll("Department", "IT", "Finance", "Production", "Administration");
        departmentChoice.getSelectionModel().selectFirst();

        roleChoice.getItems().addAll("Role", "Assistant", "Junior", "Senior", "Lead", "Admin");
        roleChoice.getSelectionModel().selectFirst();
    }

    @FXML protected void onAddClick() throws IOException
    {
        if (validInput())
        {
            Staff newStaff = new Staff
            (
                new Person(name.getText(), surname.getText(), email.getText(), password.getText()), 
                new Department(departmentChoice.getValue(), roleChoice.getValue(), wage.getText())
            );

            String Name = newStaff.getPerson().getName();
            String Surname = newStaff.getPerson().getSurname();
            String DepartmentName = newStaff.getDepartment().getName();
            String DepartmentRole = newStaff.getDepartment().getRole();

            successMessage.setText("Successfully Added New Staff: " + 
                Name + " " + Surname + " [" +
                DepartmentName + "/" + DepartmentRole + "]"); 

            errorMessage.setText("");

            StaffManager.manager.addNew(newStaff);
            StaffStorage.storage.save();

            LogsManager.manager.addNew(new Logs(currentLoggedInStaff, "Added New Staff - " + 
                Name + " " + Surname + " [" + DepartmentName + "/" + DepartmentRole + "]"));
            LogsStorage.storage.save();

            staffTable.getItems().add(newStaff);
            staffTable.refresh();
        }
    }

    private int emptyFieldCount = 0;
    private int invalidChoiceCount = 0;
    private int invalidWageCount = 0;
    private int invalidStaffCount = 0;
    private boolean validInput()
    {
        if (name.getText().isEmpty() || surname.getText().isEmpty() 
            || email.getText().isEmpty() || password.getText().isEmpty() || wage.getText().isEmpty())
        {
            errorMessage.setText("Please Fill All Fields! (" + emptyFieldCount + ")"); 
            successMessage.setText("");

            emptyFieldCount++; 
            invalidChoiceCount = 0; invalidWageCount = 0; invalidStaffCount = 0; noStaffSelectedCount = 0;
            return false;
        }

        if (departmentChoice.getValue().equals("Department") || roleChoice.getValue().equals("Role"))
        {
            errorMessage.setText("Please Choose a Valid Choice From ChoiceBoxes! (" + invalidChoiceCount + ")");
            successMessage.setText("");

            invalidChoiceCount++; 
            emptyFieldCount = 0; invalidWageCount = 0; invalidStaffCount = 0; noStaffSelectedCount = 0;
            return false;
        }

        String wageRegex = "^[0-9]+(\\.[0-9]+)?$";
        if (!wage.getText().matches(wageRegex))
        {
            errorMessage.setText("Wage Must Be a Number Higher Than 0! (" + invalidWageCount + ")");
            successMessage.setText("");

            invalidWageCount++; 
            emptyFieldCount = 0; invalidChoiceCount = 0; invalidStaffCount = 0; noStaffSelectedCount = 0;
            return false;
        }

        Staff currentStaff = new Staff(
            new Person(name.getText(), surname.getText(), email.getText(), password.getText()), 
            new Department(departmentChoice.getValue(), roleChoice.getValue(), wage.getText()));

        if (StaffManager.manager.exists(currentStaff))
        {
            errorMessage.setText("This Staff Already Exists! (" + invalidStaffCount + ")"); 
            successMessage.setText("");

            invalidStaffCount++; 
            invalidChoiceCount = 0; invalidWageCount = 0; emptyFieldCount = 0; noStaffSelectedCount = 0;
            return false;
        }
        return true;
    }

    private int noStaffSelectedCount = 0;
    @FXML protected void onRemoveClick() throws IOException
    {
        Staff selected = staffTable.getSelectionModel().getSelectedItem();
        if (selected == null || selected == currentLoggedInStaff)
        {
            if (selected == currentLoggedInStaff)
                errorMessage.setText("You Can't Remove Yourself! (" + noStaffSelectedCount + ")");
            else 
                errorMessage.setText("Select a User To Remove! (" + noStaffSelectedCount + ")");

            successMessage.setText("");

            noStaffSelectedCount++; 
            invalidChoiceCount = 0; invalidWageCount = 0; emptyFieldCount = 0; invalidStaffCount = 0;
            return;
        }

        String Name = selected.getPerson().getName();
        String Surname = selected.getPerson().getSurname();
        String DepartmentName = selected.getDepartment().getName();
        String DepartmentRole = selected.getDepartment().getRole();

        successMessage.setText(Name + " " + Surname + " [" + 
            DepartmentName + "/" + DepartmentRole + "] Successfully Removed!"); 

        errorMessage.setText("");

        StaffManager.manager.remove(selected);
        StaffStorage.storage.save();

        LogsManager.manager.addNew(new Logs(currentLoggedInStaff, "Removed Staff - " + 
            Name + " " + Surname + " [" + DepartmentName + "/" + DepartmentRole + "]"));
        LogsStorage.storage.save();

        staffTable.getItems().remove(selected);
        staffTable.refresh();
    }

    @FXML protected void onLogsClick() throws IOException 
    {
        Stage stage = (Stage) email.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Admin/logsScreen.fxml"));
        Scene scene = new Scene(loader.load(), 880, 625);
        stage.setTitle("Logs");
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