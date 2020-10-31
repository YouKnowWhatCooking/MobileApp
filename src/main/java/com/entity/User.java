package com.entity;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="balance")
    private int balance;

    @Column(name="last_login")
    private Date lastLogin;

    @OneToOne
    private Bonus bonus;

    @OneToOne
    private Role role;

    public User(String username, String password, String name, int balance, Date lastLogin, Bonus bonus, Role role) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.balance = balance;
        this.lastLogin = lastLogin;
        this.bonus = bonus;
        this.role = role;
    }

    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
