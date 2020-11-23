package com.payload;

public class QuestionPayLoad {
    private int id;
    private String text;
    private int categoryID;


    public QuestionPayLoad(int id, String text, int categoryID) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
