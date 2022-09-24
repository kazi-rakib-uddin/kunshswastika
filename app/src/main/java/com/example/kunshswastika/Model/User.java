package com.example.kunshswastika.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private String user_id, phone_no, user_name, u_id;
    private String refer_by, sponsors;

    private Context context;

    public SharedPreferences sharedPreferences;


    public User(Context context) {

        this.context=context;

        sharedPreferences=context.getSharedPreferences("login_details", Context.MODE_PRIVATE);

    }

    public String getUser_id() {
        user_id=sharedPreferences.getString("user_id","");
        return user_id;
    }

    public void setUser_id(String user_id) {
        sharedPreferences.edit().putString("user_id",user_id).commit();
        this.user_id = user_id;
    }


    public String getUser_name() {
        user_name=sharedPreferences.getString("user_name","");
        return user_name;
    }

    public void setUser_name(String user_name) {
        sharedPreferences.edit().putString("user_name",user_name).commit();
        this.user_name = user_name;
    }


    public String getPhone_no() {

        phone_no = sharedPreferences.getString("phone_no","");
        return phone_no;
    }

    public void setPhone_no(String phone_no) {

        sharedPreferences.edit().putString("phone_no",phone_no).commit();
        this.phone_no = phone_no;
    }


    public String getRefer_by() {

        refer_by = sharedPreferences.getString("refer_by","");
        return refer_by;
    }

    public void setRefer_by(String refer_by) {

        sharedPreferences.edit().putString("refer_by",refer_by).commit();
        this.refer_by = refer_by;
    }

    public String getU_id() {
        u_id = sharedPreferences.getString("u_id","");
        return u_id;
    }

    public void setU_id(String u_id) {
        sharedPreferences.edit().putString("u_id",u_id).commit();
        this.u_id = u_id;
    }

    public  void  remove(){
        sharedPreferences.edit().clear().commit();
    }


}
