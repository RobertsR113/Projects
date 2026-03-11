package com.example;

import java.util.HashMap;
import java.util.Map;

public class MoneyStorage
{

    private static Map<Integer, Integer> banknotes = new HashMap<>();

    private static boolean initialized = false;

    static
    {
        if (!initialized)
        {
            initializeBanknotes();
            initialized = true;
        }
    }

    private static void initializeBanknotes()
    {
        banknotes.put(500, 10);
        banknotes.put(100, 10);
        banknotes.put(50, 10);
        banknotes.put(20, 10);
        banknotes.put(10, 10);
        banknotes.put(5, 10);
    }

    public static Map<Integer, Integer> getBanknotes()
    {
        return banknotes;
    }

    public static void updateBanknotes(int denomination, int count)
    {
        banknotes.put(denomination, count);
        LogStorage.addLog("Admin refilled " + count + " banknotes of " + denomination + " €");
    }

    public static int getTotalBalance()
    {
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : banknotes.entrySet())
        {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }
}
