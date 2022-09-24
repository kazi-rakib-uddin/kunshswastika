package com.example.kunshswastika.Model;

public class Requested_Model {

    String id, name, quantity, date,  my_refer_code, u_id;

    public Requested_Model(String id, String name, String quantity, String date, String my_refer_code, String u_id) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.my_refer_code = my_refer_code;
        this.u_id = u_id;
    }


    public String getId() {
        return id;
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


    public String getMy_refer_code() {
        return my_refer_code;
    }

    public String getU_id() {
        return u_id;
    }
}
