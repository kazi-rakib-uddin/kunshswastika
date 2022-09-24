package com.example.kunshswastika.Model;

public class HomeCatagoryModel {

    String id,name,image;

    public HomeCatagoryModel(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
