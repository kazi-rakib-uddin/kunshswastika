package com.example.kunshswastika.Model;

public class Product_Model {

    String product_id, product_name, product_image, selling_price, mrp;

    public Product_Model(String product_id, String product_name, String product_image, String selling_price, String mrp) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.selling_price = selling_price;
        this.mrp = mrp;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public String getMrp() {
        return mrp;
    }
}
