package BankSimulator;

public abstract class Account
{
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber)
    {
        this.accountNumber = accountNumber;
        balance = 0;
    }

    public boolean deposit(double amount)
    {
        if (amount > 0)
        {
            balance += amount;
            System.out.println("Successfully added money!");
            return true;
        }
        else
        {
            System.out.println("Invalid amount!");
            return false;
        }
    }

    public abstract boolean withdraw (double amount);

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    protected void setBalance(double balance)
    {
        this.balance = balance;
    }

}
