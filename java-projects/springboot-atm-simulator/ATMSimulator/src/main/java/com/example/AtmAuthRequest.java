package com.example;

public class AtmAuthRequest
{
    private String cardNumber;
    private String pin;

    public AtmAuthRequest()
    {
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
}