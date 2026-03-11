package com.example;

import java.io.Serializable;

public class Client implements Serializable
{
    private String cardNumber;
    private String pin;
    private int balance;

    public Client()
    {
    }

    public Client(String cardNumber, String pin, int balance)
    {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public boolean isPinCorrect(String inputPin)
    {
        return this.pin.equals(inputPin);
    }

    @Override
    public String toString()
    {
        return "Client{" + "cardNumber='" + cardNumber + '\'' + ", pin='" + pin + '\'' + ", balance=" + balance + '}';
    }
}
