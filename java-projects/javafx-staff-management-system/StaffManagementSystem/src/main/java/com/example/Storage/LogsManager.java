package com.example.Storage;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.Models.Logs;

public class LogsManager implements Serializable
{
    public static LogsManager manager = new LogsManager();
    private ArrayList<Logs> logsList;

    public ArrayList<Logs> getLogsList() 
    {
        if (logsList == null)
            return logsList = new ArrayList<>();

        return logsList;
    }

    public void setLogsList(ArrayList<Logs> logs) 
    {
        logsList = logs;
    }

    public void addNew(Logs newLog)
    {
        if (logsList == null)
            logsList = new ArrayList<>();

        logsList.add(newLog);

        System.out.println("\n--------------------------------------\n" + 
            newLog.display() +
            "\n--------------------------------------\n");
    };
}
