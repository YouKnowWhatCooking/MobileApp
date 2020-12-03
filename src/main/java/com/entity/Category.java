package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Column(name = "Title")
    private String title;

    @Column(name = "Price")
    private int price;

    @Column(name = "Purchase_requirment")
    private boolean purchaseRequirment;

    @ManyToOne
    private User user;

    public Category() {
    }

    public Category(String title, int price, boolean purchaseRequirment, User user) {
        super();
        this.title = title;
        this.purchaseRequirment = purchaseRequirment;
        this.price = price;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPurchaseRequirment() {
        return purchaseRequirment;
    }

    public void setPurchaseRequirment(boolean purchaseRequirment) {
        this.purchaseRequirment = purchaseRequirment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
