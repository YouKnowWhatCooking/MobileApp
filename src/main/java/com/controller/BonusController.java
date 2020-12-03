package com.controller;


import com.entity.Bonus;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.Response;
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
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        int userBonuses = user.getBalance();
        return ResponseEntity.ok(userBonuses);
    }

    @GetMapping("/bonus")
    public ResponseEntity<Response> gainBonus(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        long lastLogin = user.getLastBonusGain();
        long currentDate = System.currentTimeMillis();
        if ((currentDate - lastLogin) > 86400000) {
            user.setLastBonusGain(lastLogin);
            Bonus foundBonus = bonusRepository.findById(user.getBonus().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
            int gainingBonus = foundBonus.getBonusValue();
            user.setBalance(user.getBalance() + foundBonus.getBonusValue());
            Bonus nextBonus = bonusRepository.findById((user.getBonus().getId() + 1) % 7)
                    .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
            user.setBonus(nextBonus);
            user.setLastBonusGain(System.currentTimeMillis());
            this.userRepository.save(user);
            Response response = new Response("Вам зачислен бонус - " + gainingBonus + " монет");
            return ResponseEntity.ok(response);
        } else if ((currentDate - lastLogin) > 86400000 * 2) {
            user.setLastBonusGain(lastLogin);
            Bonus foundBonus = bonusRepository.findById(user.getBonus().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
            int gainingBonus = foundBonus.getBonusValue();
            user.setBalance(user.getBalance() + foundBonus.getBonusValue());
            Bonus nextBonus = bonusRepository.findById((user.getBonus().getId() + 1) % 7)
                    .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
            user.setBonus(nextBonus);
            user.setLastBonusGain(System.currentTimeMillis());
            this.userRepository.save(user);
            Response response = new Response("Вам зачислен бонус - " + gainingBonus + " монет");
            return ResponseEntity.ok(response);
        } else {
            Response response = new Response("No bonus yet");
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping
    public ResponseEntity<?> createBonus(@RequestBody Bonus bonus) {
        Bonus newBonus = new Bonus();
        newBonus.setBonusValue(bonus.getBonusValue());
        this.bonusRepository.save(newBonus);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateBonus(@RequestBody Bonus bonus) {
        Bonus existingBonus = bonusRepository.findById(bonus.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
        existingBonus.setBonusValue(bonus.getBonusValue());
        this.bonusRepository.save(existingBonus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBonus(@PathVariable("id") int ID) {
        Bonus existingBonus = bonusRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
        bonusRepository.delete(existingBonus);
        return ResponseEntity.ok().build();
    }
}
