package com.entity;


import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="username")
    @NotNull()
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="balance")
    private int balance;

    @Column(name="lastBonusGain")
    private Long lastBonusGain;

    @OneToOne
    private Bonus bonus;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> role;

    public User() {
    }

    public User(String username, String password, String name, int balance, Long lastBonusGain, Bonus bonus) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.balance = balance;
        this.lastBonusGain = lastBonusGain;
        this.bonus = bonus;
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

    public Long getLastBonusGain() {
        return lastBonusGain;
    }

    public void setLastBonusGain(Long lastBonusGain) {
        this.lastBonusGain = lastBonusGain;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
