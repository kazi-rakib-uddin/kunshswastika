package com.example.kunshswastika.Model;

public class Bank_Details_Model {

    String user_id, name, bank_name, ifsc_code, account_no;

    public Bank_Details_Model(String user_id, String name, String bank_name, String ifsc_code, String account_no) {
        this.user_id = user_id;
        this.name = name;
        this.bank_name = bank_name;
        this.ifsc_code = ifsc_code;
        this.account_no = account_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public String getAccount_no() {
        return account_no;
    }
}
