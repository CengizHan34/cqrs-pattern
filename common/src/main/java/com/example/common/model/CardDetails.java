package com.example.common.model;

import java.io.Serializable;

public class CardDetails implements Serializable {
    private String name;
    private String cardNumber;
    private String validUntilMonth;
    private int cvv;

    public CardDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValidUntilMonth() {
        return validUntilMonth;
    }

    public void setValidUntilMonth(String validUntilMonth) {
        this.validUntilMonth = validUntilMonth;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
