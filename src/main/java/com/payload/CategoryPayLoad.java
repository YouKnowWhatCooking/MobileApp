package com.payload;

public class CategoryPayLoad {
    private String title;
    private String username;
    private int price;
    private boolean purchaseRequirement;

    public CategoryPayLoad(String title, String username, int price, boolean purchaseRequirement) {
        this.title = title;
        this.username = username;
        this.price = price;
        this.purchaseRequirement = purchaseRequirement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUserID(String username) {
        this.username = username;
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
}
