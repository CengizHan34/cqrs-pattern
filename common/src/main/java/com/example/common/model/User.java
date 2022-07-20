package com.example.common.model;


import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String name;
    private CardDetails cardDetails;

    public User() {
    }

    public User(String userId, String name, CardDetails cardDetails) {
        this.userId = userId;
        this.name = name;
        this.cardDetails = cardDetails;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", cardDetails=" + cardDetails +
                '}';
    }
}
