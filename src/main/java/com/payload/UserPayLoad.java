package com.payload;

import java.util.Date;

public class UserPayLoad {
    private String username;
    private String password;
    private String name;

    public UserPayLoad(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public UserPayLoad() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }


}
