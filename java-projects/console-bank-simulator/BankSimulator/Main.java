package BankSimulator;

import java.util.Scanner;

public class Main
{

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        Bank bank = new Bank("Seb", "ULA005");

        boolean exit = false;
        int choice;
        while(!exit)
        {
            showMenu();
            System.out.println("\nEnter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice)
            {
                case 1:
                    bank.createAccountForCustomer();
                    break;
                case 2:
                    bank.processDeposit();
                    break;
                case 3:
                    bank.processWithdrawal();
                    break;
                case 4:
                    bank.showCustomerBalance();
                    break;
                case 5:
                    bank.showAllTransactions();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    exit = true;
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void showMenu()
    {
        System.out.println(" ");
        System.out.println("====--- Banking Menu---====");
        System.out.println("1. Create an account for customer");
        System.out.println("2. Process deposit");
        System.out.println("3. Process withdrawal");
        System.out.println("4. Show customer balance");
        System.out.println("5. Show all transactions");
        System.out.println("6. Exit");
        System.out.println("====--- Banking Menu---====");
    }
}
