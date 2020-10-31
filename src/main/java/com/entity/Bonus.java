package com.entity;


import javax.persistence.*;

@Entity
@Table(name="Bonus")
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="bonus_value")
    private int bonusValue;

    public Bonus(){

    }

    public Bonus(int bonusValue){
        super();
        this.bonusValue = bonusValue;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(int bonusValue) {
        this.bonusValue = bonusValue;
    }
}
