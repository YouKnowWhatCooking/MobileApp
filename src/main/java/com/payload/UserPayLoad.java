package com.payload;

import java.util.Date;

public class UserPayLoad {
    private String username;
    private String password;
    private String name;
    private int balance;
    private Date lastLogin;
    private int bonus_id;
    private int role_id;

    public UserPayLoad(String username, String password, String name, int balance, Date lastLogin, int bonus_id, int role_id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.balance = balance;
        this.lastLogin = lastLogin;
        this.bonus_id = bonus_id;
        this.role_id = role_id;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getBonus_id() {
        return bonus_id;
    }

    public void setBonus_id(int bonus_id) {
        this.bonus_id = bonus_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
