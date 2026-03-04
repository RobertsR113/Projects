package BankSimulator;

import java.util.Scanner;

public class InputUtils
{
    private static final Scanner sc = new Scanner(System.in);

    public static String getTextInput(String message)
    {
        String input;
        do
        {
            System.out.println(message);
            input = sc.nextLine().trim();
            if (input.isEmpty())
            {
                System.out.println("Invalid name! Try again!");
                sc.nextLine();
            }
        }
        while (input.isEmpty());
        return input;
    }

    public static double getDoubleInput(String message)
    {
        double input = 0;
        boolean valid = false;

        while (!valid)
        {
            System.out.println(message);
            if (sc.hasNextDouble())
            {
                input = sc.nextDouble();
                valid = true;
            }
            else
            {
                System.out.println("Invalid double input try again!");
                sc.nextLine();
            }
        }
        return input;
    }

    public static int GetNumberInputWithinRange(String message, int lowerBound, int higherBound)
    {
        int input = 0;
        boolean valid = false;

        while (!valid)
        {
            System.out.println(message);

            if (sc.hasNextInt())
            {
                input = sc.nextInt();
                if (input >= lowerBound && input <= higherBound)
                {
                    valid = true;
                }
                else
                    System.out.println("Input out of range!");
            }
            else
                System.out.println("Invalid range input!");
        }
        sc.nextLine();
        return input;
    }

}
