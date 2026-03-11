package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientStorage
{
    private static final String filePath = "A_ClientStorage.txt";

    public static List<Client> readClients()
    {
        List<Client> clients;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)))
        {
            @SuppressWarnings("unchecked")
            List<Client> loadedClients = (List<Client>) ois.readObject();
            System.out.println("Loading EXISTING client list!");
            clients = loadedClients;
        } catch (Exception e)
        {
            System.out.println("No existing client storage! Created NEW one.");
            clients = new ArrayList<>();
        }

        boolean adminExists = clients.stream().anyMatch(client -> "admin".equals(client.getCardNumber()));
        if (!adminExists)
        {
            Client admin = new Client("admin", "admin123", 0); // No balance needed for admin
            clients.add(admin);
            saveClients(clients);
            System.out.println("Admin account added to client storage.");
        }

        return clients;
    }

    public static void saveClients(List<Client> clients)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath)))
        {
            System.out.println("Successfully SAVED to CLIENT storage!");
            oos.writeObject(clients);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
