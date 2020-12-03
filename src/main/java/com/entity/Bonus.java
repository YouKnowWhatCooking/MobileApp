package com.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Bonus")
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "bonus_value")
    @NotNull(message = "bonusValue cannot be null")
    @Pattern(regexp = "\\D", message = "Value must be a number")
    private int bonusValue;

    public Bonus() {

    }

    public Bonus(int bonusValue) {
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
