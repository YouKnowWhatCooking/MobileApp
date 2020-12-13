package com.payload;

public class CategoryPayLoad {
    private int id;
    private String title;
    private int price;
    private boolean purchaseRequirment;

    public CategoryPayLoad(int id, String title, int price, boolean purchaseRequirment) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.purchaseRequirment = purchaseRequirment;
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

    public boolean isPurchaseRequirement() {
        return purchaseRequirment;
    }

    public void setPurchaseRequirement(boolean purchaseRequirement) {
        this.purchaseRequirment = purchaseRequirement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
