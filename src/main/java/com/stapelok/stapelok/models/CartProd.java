package com.stapelok.stapelok.models;

import java.util.Arrays;

public class CartProd {

    private Long product_id;

    private String script_id;


    private Long id_cart;

    private String name;

    private String price;

    private String count;

    private byte[] image;

    public String getScript_id() {
        return script_id;
    }

    public void setScript_id(String script_id) {
        this.script_id = script_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public CartProd(Long product_id, String script_id, Long id_cart, String name, String price, String count, byte[] image) {
        this.product_id = product_id;
        this.script_id = script_id;
        this.id_cart = id_cart;
        this.name = name;
        this.price = price;
        this.count = count;
        this.image = image;
    }

    public Long getId_cart() {
        return id_cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_cart(Long id_cart) {
        this.id_cart = id_cart;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CartProd{" +
                "product_id=" + product_id +
                ", id_cart=" + id_cart +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", count='" + count + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public CartProd() {
    }
}
