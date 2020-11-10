package com.controller;

import com.config.JwtTokenUtil;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.UserPayLoad;
import com.repository.BonusRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }


    @GetMapping("/lk")
    public User getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String currentUsername = jwtTokenUtil.getUsernameFromToken(token);
        return this.userRepository.findByUsername(currentUsername);
    }


   @PostMapping("/registration")
    public User register(@RequestBody UserPayLoad userPayLoad){
        User user = new User(userPayLoad.getUsername(), userPayLoad.getPassword(), userPayLoad.getName(), 0, userPayLoad.getLastLogin(), bonusRepository.findById(userPayLoad.getBonus_id()), roleRepository.findById(userPayLoad.getRole_id()));
        userService.save(user);
        return this.userRepository.findByUsername(user.getUsername());
    }

   @PutMapping("/{id}")
    public User modifyProfile(@RequestBody User user, @PathVariable("id") int ID){
       User existingUser = this.userRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + ID));
        existingUser.setName(user.getName());
        return this.userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int ID) {
        User existingUser = this.userRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + ID));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }

}
