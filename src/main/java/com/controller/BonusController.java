package com.controller;


import com.entity.Bonus;
import com.entity.User;
import com.repository.BonusRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/bonuses")
public class BonusController {

    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<?> getAllBonuses() {
        List<Bonus> bonusList = bonusRepository.findAll();
        return ResponseEntity.ok(bonusList);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUserBonuses(HttpServletRequest request) {
        User user  = userRepository.findByUsername(request.getRemoteUser());
        int userBonuses = user.getBalance();
        return ResponseEntity.ok(userBonuses);
    }

    @GetMapping("/bonus")
    public ResponseEntity<?> gainBonus(HttpServletRequest request) {
        User user  = userRepository.findByUsername(request.getRemoteUser());
        long lastLogin = user.getLastBonusGain();
        long currentDate = System.currentTimeMillis();
        if ((currentDate - lastLogin) > 86400000) {
            user.setLastBonusGain(lastLogin);
            int gainingBonus = bonusRepository.findById(user.getBonus().getId()).getBonusValue();
            user.setBalance(user.getBalance() + bonusRepository.findById(user.getBonus().getId()).getBonusValue());
            user.setBonus(bonusRepository.findById((user.getBonus().getId()+1)%7));
            user.setLastBonusGain(System.currentTimeMillis());
            this.userRepository.save(user);
            return ResponseEntity.ok("Вам зачислен бонус - " + gainingBonus  + " монет");
        } else if ((currentDate - lastLogin) > 86400000 * 2) {
            user.setLastBonusGain(lastLogin);
            int gainingBonus = bonusRepository.findById(user.getBonus().getId()).getBonusValue();
            user.setBalance(user.getBalance() + bonusRepository.findById(user.getBonus().getId()).getBonusValue());
            user.setBonus(bonusRepository.findById(1));
            user.setLastBonusGain(System.currentTimeMillis());
            this.userRepository.save(user);
            return ResponseEntity.ok("Вам зачислен бонус - " + gainingBonus  + " монет");
        } else return ResponseEntity.ok("No bonus");
    }


    @PostMapping
    public ResponseEntity<?> createBonus(@RequestBody Bonus bonus){
        Bonus newBonus = new Bonus();
        newBonus.setBonusValue(bonus.getBonusValue());
        this.bonusRepository.save(newBonus);
        return ResponseEntity.ok("Success");
    }

    @PutMapping
    public ResponseEntity<?> updateBonus(@RequestBody Bonus bonus){
        Bonus existingBonus = bonusRepository.findById(bonus.getId());
        existingBonus.setBonusValue(bonus.getBonusValue());
        this.bonusRepository.save(existingBonus);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBonus(@PathVariable("id") int ID) {
        Bonus existingBonus = bonusRepository.findById(ID);
        bonusRepository.delete(existingBonus);
        return ResponseEntity.ok("Success");
    }
}
