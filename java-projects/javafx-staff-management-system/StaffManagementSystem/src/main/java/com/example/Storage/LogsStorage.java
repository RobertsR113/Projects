package com.example.Storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.Models.Logs;

public class LogsStorage implements Serializable
{
    public final static LogsStorage storage = new LogsStorage();
    private final String logsFile = "1_logs.txt";

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void save() throws IOException
    {
        String time = LocalDateTime.now().format(timeFormat);

        try (FileOutputStream fos = new FileOutputStream(logsFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);)
        {
            oos.writeObject(LogsManager.manager.getLogsList());
            System.out.println("\n" + time + " Successfully Saved To '" + logsFile + "'");
        } 
    }

    public void load() throws IOException
    {
        String time = LocalDateTime.now().format(timeFormat);

        if (!Files.exists(Path.of(logsFile))) 
        {
            System.out.println("\n" + time + " " + "Created New File '" + logsFile + "'");
            LogsManager.manager.setLogsList(new ArrayList<>());
            return;
        }

        try (FileInputStream fis = new FileInputStream(logsFile);
            ObjectInputStream ois = new ObjectInputStream(fis);) 
        {
            @SuppressWarnings("unchecked")
            ArrayList<Logs> loaded = (ArrayList<Logs>) ois.readObject();
            
            if (loaded == null)
                LogsManager.manager.setLogsList(new ArrayList<>());
            else
                LogsManager.manager.setLogsList(loaded);

            System.out.println("\n" + time + " Successfully Loaded ''" + logsFile + "'");
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException(e);
        }
    }
}
