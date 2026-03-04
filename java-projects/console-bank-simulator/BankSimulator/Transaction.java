package BankSimulator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction
{
    private final UUID transactionId;
    private final TransactionType transactionType;
    private final double transactionAmount;
    private final Account account;
    private final Customer customer;
    private final LocalDateTime dataTime;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd   hh:mm:ss");

    public Transaction(UUID transactionId, TransactionType transactionType, double transactionAmount,LocalDateTime dataTime,  Account account, Customer customer)
    {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.dataTime = dataTime;
        this.account = account;
        this.customer = customer;
    }

    public void showTransactionInfo() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Type: " + transactionType);
        System.out.println("Amount: $" + String.format("%.2f", transactionAmount));
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Customer: " + customer.getName());
        System.out.println("Date: " + dataTime.format(dtf));
    }

}
