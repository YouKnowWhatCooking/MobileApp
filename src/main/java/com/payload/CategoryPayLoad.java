package com.payload;

public class CategoryPayLoad {
    private int id;
    private String title;
    private int price;
    private boolean purchaseRequirement;

    public CategoryPayLoad(int id, String title, int price, boolean purchaseRequirement) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.purchaseRequirement = purchaseRequirement;
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
        return purchaseRequirement;
    }

    public void setPurchaseRequirement(boolean purchaseRequirement) {
        this.purchaseRequirement = purchaseRequirement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
