package BankSimulator;

public class SavingAccount extends Account
{
    private static final double Interest_rate = 0.02;

    public SavingAccount(String accountNumber)
    {
        super(accountNumber);
    }

    @Override
    public boolean withdraw(double amount)
    {
        if (amount > 0)
        {
            if (getBalance() > amount)
            {
                setBalance(getBalance() - amount);
                System.out.println("$" + String.format("%.2f", amount) + " has been withdrawn from your account");
                double remainingBalance = getBalance() + (getBalance() * Interest_rate);
                setBalance(remainingBalance);
                System.out.println("Balance (After interest rate increase): $" + String.format("%.2f", remainingBalance));
                return true;
            }
            else
            {
                System.out.println("Insufficient funds!");
                System.out.println("Your balance is: $" + String.format("%.2f", getBalance()));
                return false;
            }
        }
        System.out.println("Invalid amount!");
        return false;
    }

}
