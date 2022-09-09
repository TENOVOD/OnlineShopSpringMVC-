package com.stapelok.stapelok.models;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name="products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="p_type")
    private String type;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private String price;

    @Column(name="p_w_sale")
    private String p_w_sale;

    @Column(name="stocks")
    private String stocks;

    @Column(name="stock_status")
    private String stock_status;

    @Column(name="date_posting")
    private Date date;

    @Column(name="country")
    private String country;

    @Column(name="composition")
    private String composition;

    @Column(name="width")
    private String width;

    @Column(name="density")
    private String density;

    @Lob
    @Column(name="image1", length = 100000)
    private byte[] image1;

    @Lob
    @Column(name="image2", length = 100000)
    private byte[] image2;

    @Lob
    @Column(name="image3", length = 100000)
    private byte[] image3;

    public Products(String type, String title,String description, String price, String p_w_sale, String stocks, String stock_status, Date date, String country, String composition, String width, String density, byte[] image1, byte[] image2, byte[] image3) {
        this.type = type;
        this.title = title;
        this.description=description;
        this.price = price;
        this.p_w_sale = p_w_sale;
        this.stocks = stocks;
        this.stock_status = stock_status;
        this.date = date;
        this.country = country;
        this.composition = composition;
        this.width = width;
        this.density = density;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Products(Long id, String type, String title, String description, String price, String p_w_sale, String stocks, String stock_status, Date date, String country, String composition, String width, String density, byte[] image1, byte[] image2, byte[] image3) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.p_w_sale = p_w_sale;
        this.stocks = stocks;
        this.stock_status = stock_status;
        this.date = date;
        this.country = country;
        this.composition = composition;
        this.width = width;
        this.density = density;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Products() {
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", p_w_sale='" + p_w_sale + '\'' +
                ", stocks='" + stocks + '\'' +
                ", stock_status='" + stock_status + '\'' +
                ", date=" + date +
                ", country='" + country + '\'' +
                ", composition='" + composition + '\'' +
                ", width='" + width + '\'' +
                ", density='" + density + '\'' +
                ", image1=" + Arrays.toString(image1) +
                ", image2=" + Arrays.toString(image2) +
                ", image3=" + Arrays.toString(image3) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getP_w_sale() {
        return p_w_sale;
    }

    public void setP_w_sale(String p_w_sale) {
        this.p_w_sale = p_w_sale;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }
}
