package com.stapelok.stapelok.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Cart {

   @Id
   @Column(name="id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

    @Column(name = "user_id")
    private long user_id;

    @Column(name="id_prod")
    private long id_prod;

    @Column(name="quantity")
    private String quantity;

    @Column(name = "add_date")
    private Date add_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_prod() {
        return id_prod;
    }

    public void setId_prod(long id_prod) {
        this.id_prod = id_prod;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Cart(long user_id, long id_prod, String quantity, Date add_date) {
        this.user_id = user_id;
        this.id_prod = id_prod;
        this.quantity = quantity;
        this.add_date = add_date;
    }

    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", id_prod=" + id_prod +
                ", quantity='" + quantity + '\'' +
                ", add_date=" + add_date +
                '}';
    }
}
