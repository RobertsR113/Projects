package com.example.Staff;

import java.io.IOException;

import com.example.Models.Staff;
import com.example.Storage.StaffManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class staffList 
{
    @FXML private TableView<Staff> staffTable;

    @FXML private TableColumn<Staff, String> colName;
    @FXML private TableColumn<Staff, String> colSurname;
    @FXML private TableColumn<Staff, String> colEmail;
    @FXML private TableColumn<Staff, String> colDepartment;
    @FXML private TableColumn<Staff, String> colRole;
    @FXML private TableColumn<Staff, String> colWage;

    private final ObservableList<Staff> staffObservableList = FXCollections.observableArrayList();
    private final Staff currentLoggedInStaff = StaffManager.manager.getLoggedInStaff();

    @FXML public void initialize()
    {
        for (Staff staff : StaffManager.manager.getStaffList()) 
        {
            if (staff.getDepartmentName().equals(currentLoggedInStaff.getDepartmentName())) 
                staffObservableList.add(staff);
        }

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colWage.setCellValueFactory(new PropertyValueFactory<>("wage"));

        staffTable.setItems(staffObservableList);
        staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML protected void onExitClick() throws IOException 
    {
        Stage stage = (Stage) staffTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Staff/mainScreen.fxml"));
        Scene scene = new Scene(loader.load(), 519, 316);
        stage.setTitle("Info");
        stage.setScene(scene);
        stage.show();
    }
}
