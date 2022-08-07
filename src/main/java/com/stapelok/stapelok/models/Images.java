package com.stapelok.stapelok.models;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="id_products")
    private int id_products;

    @Column(name="byte_code")
    private byte[] byte_code;

    public int getId_products() {
        return id_products;
    }

    public void setId_products(int id_products) {
        this.id_products = id_products;
    }

    public byte[] getByte_code() {
        return byte_code;
    }

    public void setByte_code(byte[] byte_code) {
        this.byte_code = byte_code;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", id_products=" + id_products +
                ", byte_code=" + Arrays.toString(byte_code) +
                '}';
    }

    public Images() {
    }

    public Images(Long id, int id_products, byte[] byte_code) {
        this.id = id;
        this.id_products = id_products;
        this.byte_code = byte_code;
    }

    public Long getId() {
        return id;
    }
}
