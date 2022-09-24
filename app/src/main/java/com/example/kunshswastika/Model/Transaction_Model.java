package com.example.kunshswastika.Model;

public class Transaction_Model {

    String name, type, level, amount, date;

    public Transaction_Model(String name, String type, String level, String amount, String date) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
