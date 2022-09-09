package com.stapelok.stapelok.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="user_id")
    private long user_id;
    @Column(name="user_ac_id")
    private String user_ac_id;
    @Column(name="total_sum")
    private String total_sum;
    @Column(name= "first_name")
    private String name;
    @Column(name="last_name")
    private String last_name;
    @Column(name= "middle_name")
    private String middle_name;
    @Column(name="address")
    private String address;
    @Column(name="phone_num")
    private String phone_num;
    @Column(name="comment")
    private String comment;
    @Column(name="status")
    private String status;
    @Column(name= "add_date")
    private Date date;

    @Column(name="delivery")
    private String delivery;

    public Order(long user_id, String user_ac_id, String total_sum, String name, String last_name, String middle_name, String address, String phone_num, String comment, String status, Date date, String delivery) {
        this.user_id = user_id;
        this.user_ac_id = user_ac_id;
        this.total_sum = total_sum;
        this.name = name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.address = address;
        this.phone_num = phone_num;
        this.comment = comment;
        this.status = status;
        this.date = date;
        this.delivery = delivery;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getTotal_sum() {
        return total_sum;
    }

    public void setTotal_sum(String total_sum) {
        this.total_sum = total_sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser_ac_id() {
        return user_ac_id;
    }

    public void setUser_ac_id(String user_ac_id) {
        this.user_ac_id = user_ac_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", user_ac_id=" + user_ac_id +
                ", total_sum='" + total_sum + '\'' +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", address='" + address + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", delivery='" + delivery + '\'' +
                '}';
    }
}
