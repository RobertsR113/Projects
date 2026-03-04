package BankSimulator;

import java.time.LocalDateTime;
import java.util.*;

public class Bank
{
    private static String bankCode;
    private static String branchCode;
    private static Map<Integer, Customer> customers;
    private static List<Transaction> transactions;
    private static int accountCounter;

    static
    {
        accountCounter = 5000;
    }

    public Bank(String bankCode, String branchCode)
    {
        Bank.bankCode = bankCode;
        Bank.branchCode = branchCode;
        customers = new HashMap<>();
        transactions = new ArrayList<>();
    }

    private String generateAccountNumber()
    {
        return bankCode + branchCode + accountCounter++;
    }

    private Customer findCustomer(int customerId)
    {
        return customers.get(customerId);
    }

    public void createAccountForCustomer()
    {
        int customerId = InputUtils.GetNumberInputWithinRange("Enter customer ID(1000-9999): ", 1000, 9999);

        Customer customer = findCustomer(customerId);

        if (customer == null)
        {
            String customerName = InputUtils.getTextInput("Enter customer name: ");
            customer = new Customer(customerName, customerId);
            customers.put(customerId, customer);
            System.out.println("New customer created: " + customerName);
        }

        String accountType = InputUtils.getTextInput("Enter account type (Checking/Savings): ");
        String accountNumber = generateAccountNumber();

        if (accountType.equalsIgnoreCase("checking"))
        {
            Account newAccount = new CheckAccount(accountNumber);
            customer.addAccount(newAccount);
            System.out.println(" ");
            System.out.println("Checking account created for customer " + customer.getName() + " with account number " + accountNumber);
        }
        else if (accountType.equalsIgnoreCase("savings"))
        {
            Account newAccount = new SavingAccount(accountNumber);
            customer.addAccount(newAccount);
            System.out.println(" ");
            System.out.println("Savings account created for customer " + customer.getName() + " with account number " + accountNumber);
        }
        else
            System.out.println("Invalid account type.");
    }



    public void processDeposit()
    {
        try
        {
            int customerId = InputUtils.GetNumberInputWithinRange("Enter customer ID(1000-9999): ", 1000, 9999);

            Customer customer = findCustomer(customerId);

            if (customer != null)
            {
                String accountNumber = InputUtils.getTextInput("Enter account number(SebULA*******): ");
                Account account = customer.getAccount(accountNumber);
                if (account != null)
                {
                    double amount = InputUtils.getDoubleInput("Enter deposit amount: ");
                    if (account.deposit(amount))
                    {
                        System.out.println(" ");
                        Transaction transaction = new Transaction(UUID.randomUUID(), TransactionType.DEPOSIT, amount, LocalDateTime.now(), account, customer);
                        transactions.add(transaction);
                        transaction.showTransactionInfo();
                    }
                }
                else
                    System.out.println("Account not found!");
            }
            else
                System.out.println("Customer not found!");
        }
        catch(Exception ex)
        {
            System.out.println("Invalid!");
        }
    }

    public void processWithdrawal()
    {
        int customerId = InputUtils.GetNumberInputWithinRange("Enter customer ID: ", 1000, 9999);

        Customer customer = findCustomer(customerId);

        if (customer != null)
        {
            String accountNumber = InputUtils.getTextInput("Enter account number(SebULA*******): ");
            Account account = customer.getAccount(accountNumber);
            if (account != null)
            {
                double amount = InputUtils.getDoubleInput("Enter withdrawal amount: ");

                boolean success = false;
                if (account instanceof CheckAccount)
                    success = account.withdraw(amount);
                else if (account instanceof SavingAccount)
                    success = account.withdraw(amount);
                if (success)
                {
                    System.out.println(" ");
                    Transaction transaction = new Transaction(UUID.randomUUID(), TransactionType.WITHDRAW, amount, LocalDateTime.now(), account, customer);
                    transactions.add(transaction);
                    transaction.showTransactionInfo();
                }
            }
            else
                System.out.println("Account not found!");
        }
        else
            System.out.println("Customer not found!");
    }



    public void showCustomerBalance()
    {
        int customerId = InputUtils.GetNumberInputWithinRange("Enter customer ID: ", 1000, 9999);

        Customer customer = findCustomer(customerId);

        if (customer != null)
        {
            String accountNumber = InputUtils.getTextInput("Enter account number(SebULA*******): ");
            Account account = customer.getAccount(accountNumber);
            if (account != null)
                System.out.println("\nAccount balance for " + accountNumber + ": $" + String.format("%.2f",  account.getBalance()));
            else
                System.out.println("Account not found!");
        }
        else
            System.out.println("Customer not found!");
    }

    public void showAllTransactions()
    {
        if (transactions.isEmpty())
            System.out.println("No transactions found.");
        else
        {
            for (Transaction transaction : transactions)
            {
                transaction.showTransactionInfo();
                System.out.println("------------------------------");
            }
        }
    }
}
