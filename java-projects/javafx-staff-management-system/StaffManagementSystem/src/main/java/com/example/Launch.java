package com.example;

import java.io.IOException;

import com.example.Models.Department;
import com.example.Models.Person;
import com.example.Models.Staff;
import com.example.Storage.LogsStorage;
import com.example.Storage.StaffManager;
import com.example.Storage.StaffStorage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
    -------------======= Staff App =======-------------

    1. Account login screen (email + password)
    2. Staff panel screen:
        * Info Screen
        * IF department leader:
            * Access to StaffList button
    3. Admin access panel screen:
        * See, Remove and Add staff
        * Promote/Demote Staff (Junior/Senior/Department Leader)
    4. Logs screen:
        * Staff logs:
            * Login attempts (success or failed)
        * Admin logs:
            * Added/Removed staff
            * Login attempts (success or failed)

    -------------======= Staff App =======-------------
*/

public class Launch extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException
    {
        Staff owner = new Staff
                (
                        new Person("Bob", "Jr", "bob@gmail.com", "Admin#1111"),
                        new Department("Administration", "Owner", "3500")
                );

        if (!StaffManager.manager.exists(owner))
            StaffManager.manager.addNew(owner);

        LogsStorage.storage.load();
        StaffStorage.storage.load();
        launch();
    }
}