package com.controller;


import com.entity.Bonus;
import com.entity.User;
import com.repository.BonusRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bonuses")
public class BonusController {

    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<Bonus> getAllBonuses() {
        return this.bonusRepository.findAll();
    }


    @GetMapping("/{username}")
    public Bonus gainBonus(@PathVariable("username") String username) {
        User user  = userRepository.findByUsername(username);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date lastLogin = user.getLastLogin();
        System.out.println(format.format(currentDate));
        System.out.println(format.format(lastLogin));
        if (!format.format(currentDate).equals(format.format(lastLogin))) {
            user.setLastLogin(lastLogin);
            user.setBalance(user.getBalance() + bonusRepository.findById(user.getBonus().getId()).getBonusValue());
            user.setBonus(bonusRepository.findById((user.getBonus().getId()+1)%7));

            userRepository.save(user);
            return this.bonusRepository.findById(user.getBonus().getId());
        }
        else return null;
    }


    @PostMapping
    public Bonus createBonus(@RequestBody Bonus bonus){
        Bonus newBonus = new Bonus(bonus.getBonusValue());
        return this.bonusRepository.save(newBonus);
    }

    @PutMapping("/{id}")
    public Bonus updateBonus(@RequestBody Bonus bonus, @PathVariable("id") int ID){
        Bonus existingBonus = this.bonusRepository.findById(ID);
        existingBonus.setBonusValue(bonus.getBonusValue());
        return this.bonusRepository.save(existingBonus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bonus> deleteBonus(@PathVariable("id") int ID) {
        Bonus existingBonus = this.bonusRepository.findById(ID);
        this.bonusRepository.delete(existingBonus);
        return ResponseEntity.ok().build();
    }
}
