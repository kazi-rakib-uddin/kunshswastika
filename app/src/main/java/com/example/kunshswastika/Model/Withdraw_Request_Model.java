package com.example.kunshswastika.Model;

public class Withdraw_Request_Model {

    String name, amount, status, date;

    public Withdraw_Request_Model(String name, String amount, String status, String date) {
        this.name = name;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
