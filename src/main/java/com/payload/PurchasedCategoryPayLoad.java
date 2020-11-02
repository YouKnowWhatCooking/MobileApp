package com.payload;

public class PurchasedCategoryPayLoad {
   private String username;
   private int categoryID;

    public PurchasedCategoryPayLoad(String username, int categoryID) {
        this.username = username;
        this.categoryID = categoryID;
    }

    public PurchasedCategoryPayLoad() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
