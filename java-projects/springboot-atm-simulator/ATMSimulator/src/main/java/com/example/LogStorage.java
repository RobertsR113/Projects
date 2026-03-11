package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogStorage
{
    private static final String filePath = "A_LogsStorage.txt";

    public static void addLog(String message)
    {
        File file = new File(filePath);

        if (!file.exists())
        {
            try
            {
                file.createNewFile();
                System.out.println("A_LogsStorage.txt created successfully!");
            }
            catch (IOException e)
            {
                System.out.println("Error creating A_LogsStorage.txt: " + e.getMessage());
            }
        }

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw))
        {
            out.println(message);
            System.out.println("Log added: " + message);
        }
        catch (IOException e)
        {
            System.out.println("Error writing to A_LogsStorage.txt: " + e.getMessage());
        }
    }

    public static List<String> getLogs()
    {
        List<String> logs = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists())
        {
            System.out.println("No logs file found. Creating A_LogsStorage.txt");
            return logs;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                logs.add(line);
            }
            System.out.println("Logs Retrieved: " + logs);
        }
        catch (IOException e)
        {
            System.out.println("Error reading A_LogsStorage.txt: " + e.getMessage());
        }
        return logs;
    }
}
