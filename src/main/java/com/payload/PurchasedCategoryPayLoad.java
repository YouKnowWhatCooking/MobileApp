package com.payload;

public class PurchasedCategoryPayLoad {
   private int categoryID;

    public PurchasedCategoryPayLoad(int categoryID) {

        this.categoryID = categoryID;
    }

    public PurchasedCategoryPayLoad() {
    }


    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
