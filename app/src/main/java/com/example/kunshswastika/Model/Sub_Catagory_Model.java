package com.example.kunshswastika.Model;

public class Sub_Catagory_Model {

    String id,cat_id,name,image;

    public Sub_Catagory_Model(String id, String cat_id, String name, String image) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
