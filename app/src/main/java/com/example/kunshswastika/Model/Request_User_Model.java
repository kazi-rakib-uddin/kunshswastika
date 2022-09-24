package com.example.kunshswastika.Model;

public class Request_User_Model {

    String name, quantity, date, status;

    public Request_User_Model(String name, String quantity, String date, String status) {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
