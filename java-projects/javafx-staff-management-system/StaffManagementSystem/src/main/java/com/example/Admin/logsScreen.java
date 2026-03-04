package com.example.Admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.Models.Logs;
import com.example.Storage.LogsManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class logsScreen
{
    @FXML private TextField day;
    @FXML private TextField month;
    @FXML private TextField year;
    @FXML private TextField second;
    @FXML private TextField minute;
    @FXML private TextField hour;
    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField message;

    @FXML private ChoiceBox<String> departmentChoice;
    @FXML private ChoiceBox<String> roleChoice;

    @FXML private ListView<Logs> logsList;
    private final ObservableList<Logs> logsObservableList = FXCollections.observableArrayList();
    private final ObservableList<Logs> logsFilteredList = FXCollections.observableArrayList();

    @FXML public void initialize()
    {
        logsObservableList.addAll(LogsManager.manager.getLogsList());
        logsList.setItems(logsObservableList);

        ArrayList<TextField> fields = new ArrayList<>(List.of(day, month, year, second, minute, hour, name, surname, message));
        ArrayList<ChoiceBox<String>> choices = new ArrayList<>(List.of(departmentChoice, roleChoice));

        for (TextField field : fields)
        {
            field.setOnKeyReleased(event -> filterList());
        }

        for (ChoiceBox<String> choice : choices)
        {
            choice.setOnAction(event -> filterList());
        }

        departmentChoice.getItems().addAll("Department", "IT", "Finance", "Production", "Administration");
        departmentChoice.getSelectionModel().selectFirst();

        roleChoice.getItems().addAll("Role", "Assistant", "Junior", "Senior", "Lead", "Admin", "Owner");
        roleChoice.getSelectionModel().selectFirst();
    }

    private void filterList()
    {
        ArrayList<Logs> foundMatches = new ArrayList<>();

        String Day = day.getText().trim();
        String Month = month.getText().trim();
        String Year = year.getText().trim();
        String Second = second.getText().trim();
        String Minute = minute.getText().trim();
        String Hour = hour.getText().trim();
        String Name = name.getText().trim().toLowerCase();
        String Surname = surname.getText().trim().toLowerCase();
        String Message = message.getText().trim().toLowerCase();

        String DepartmentChoice = "";
        if (departmentChoice.getValue() != null)
            DepartmentChoice = departmentChoice.getValue().trim();

        String RoleChoice = "";
        if (roleChoice.getValue() != null)
            RoleChoice = roleChoice.getValue().trim();

        boolean validDepChoice = false;
        boolean validRoleChoice = false;

        if (!DepartmentChoice.equals("Department") && !DepartmentChoice.isEmpty())
            validDepChoice = true;
        if (!RoleChoice.equals("Role") && !RoleChoice.isEmpty())
            validRoleChoice = true;

        for (Logs log : logsObservableList)
        {
            boolean filterMatches = true;

            if (log == null)
                continue;

            String time = log.getTime();

            if (!Day.isEmpty() && !time.substring(0, 2).contains(Day)) filterMatches = false;
            if (!Month.isEmpty() && !time.substring(3, 5).contains(Month)) filterMatches = false;
            if (!Year.isEmpty() && !time.substring(6, 10).contains(Year)) filterMatches = false;
            if (!Second.isEmpty() && !time.substring(11, 13).contains(Second)) filterMatches = false;
            if (!Minute.isEmpty() && !time.substring(14, 16).contains(Minute)) filterMatches = false;
            if (!Hour.isEmpty() && !time.substring(17, 19).contains(Hour)) filterMatches = false;

            String logMessage = log.getMessage().toLowerCase().trim();

            if (!Message.isEmpty() && !logMessage.contains(Message)) filterMatches = false;

            String staffName = log.getName().trim().toLowerCase();
            String staffSurname = log.getSurname().trim().toLowerCase();

            if (!Name.isEmpty() && !staffName.contains(Name)) filterMatches = false;
            if (!Surname.isEmpty() && !staffSurname.contains(Surname)) filterMatches = false;

            String departmentName = log.getDepartmentName().trim();
            String departmentRole = log.getDepartmentRole().trim();

            if (validDepChoice && !departmentName.equals(DepartmentChoice)) filterMatches = false;
            if (validRoleChoice && !departmentRole.equals(RoleChoice)) filterMatches = false;

            if (filterMatches)
                foundMatches.add(log);
        }

        logsFilteredList.setAll(foundMatches);
        logsList.setItems(logsFilteredList);
    }
    
    @FXML protected void onExitClick() throws IOException 
    {
        Stage stage = (Stage) logsList.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Admin/mainScreen.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 645);
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.show();
    }
}
