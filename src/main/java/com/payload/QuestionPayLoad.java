package com.payload;

public class QuestionPayLoad {
    private String text;
    private int categoryID;

    public QuestionPayLoad(String text, int categoryID) {
        this.text = text;
        this.categoryID = categoryID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
