package BankSimulator;

import java.util.ArrayList;
import java.util.List;

public class Customer
{
    private final String name;
    private final int customerId;
    private final List<Account> accounts;

    public Customer(String name, int customerId)
    {
        this.name = name;
        this.customerId = customerId;
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account)
    {
        accounts.add(account);
    }

    public Account getAccount(String accountNumber)
    {
        for (Account findAcc : accounts)
        {
            if (findAcc.getAccountNumber().equals(accountNumber))
            {
               return findAcc;
            }
        }
        return null;
    }

    public String getName()
    {
        return name;
    }
}
