package BankSimulator;

public class CheckAccount extends Account
{
    private static final double Overdraft_limit = 1000;
    private static final double Transaction_fee = 5;

    public CheckAccount(String accountNumber)
    {
        super(accountNumber);
    }

    @Override
    public boolean withdraw(double amount)
    {
        if (amount > 0)
        {
            if (amount <= (Overdraft_limit + getBalance()))
            {
                setBalance(getBalance() - (amount + Transaction_fee));
                System.out.println("$" + String.format("%.2f", amount) + " has been withdrawn from your account");
                System.out.println("Transaction fee: $" + Transaction_fee + " applied");
                System.out.println("Balance: $" + String.format("%.2f", getBalance()));
                return true;
            }
            else
            {
                System.out.println("Insufficient funds!");
                System.out.println("Your balance is: $" + String.format("%.2f", getBalance()));
                System.out.println("Maximum withdraw limit (including overdraft): $" + String.format("%.2f", (Overdraft_limit + getBalance())));
                return false;
            }
        }
        System.out.println("Invalid amount!");
        return false;
    }
}
