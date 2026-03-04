package com.example.Storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.Models.Staff;

public class StaffStorage 
{
    public final static StaffStorage storage = new StaffStorage();
    private final String staffFile = "1_staff.txt";
    
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void save() throws IOException
    {
        String time = LocalDateTime.now().format(timeFormat);

        try (FileOutputStream fos = new FileOutputStream(staffFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);)
        {
            oos.writeObject(StaffManager.manager.getStaffList());
            System.out.println("\n" + time + " Sucessfully Saved To '" + staffFile + "'");
        } 
    }

    public void load() throws IOException
    {
        String time = LocalDateTime.now().format(timeFormat);

        if (!Files.exists(Path.of(staffFile))) 
        {
            System.out.println("\n" + time + " " + "Created New File '" + staffFile + "'");
            LogsManager.manager.setLogsList(new ArrayList<>());
            return;
        }

        try (FileInputStream fis = new FileInputStream(staffFile);
            ObjectInputStream ois = new ObjectInputStream(fis)) 
        {
            @SuppressWarnings("unchecked")
            ArrayList<Staff> loaded = (ArrayList<Staff>) ois.readObject();
            
            if (loaded == null)
                StaffManager.manager.setStaffList(new ArrayList<>());
            else
                StaffManager.manager.setStaffList(loaded);

            System.out.println("\n" + time + " Sucessfully Loaded '" + staffFile + "'");
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException(e);
        }
    }

}
