package com.stapelok.stapelok.models;

import javax.persistence.*;

@Entity
@Table(name="pre_order")
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(name= "user_email")
    private String user_email;

    @Column(name="order_id")
    private  long order_id;
    @Column(name="id_prod")
    private long id_prod;

    @Column(name="count")
    private String count;

    @Column(name="price")
    private String price;

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public long getId_prod() {
        return id_prod;
    }

    public void setId_prod(long id_prod) {
        this.id_prod = id_prod;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public PreOrder() {
    }

    public PreOrder(String user_email, long order_id, long id_prod, String count, String price) {
        this.user_email = user_email;
        this.order_id = order_id;
        this.id_prod = id_prod;
        this.count = count;
        this.price = price;
    }

    @Override
    public String toString() {
        return "PreOrder{" +
                "id=" + id +
                ", user_id=" + user_email +
                ", id_prod=" + id_prod +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
