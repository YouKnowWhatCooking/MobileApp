package com.controller;

import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int ID) {
        return this.userRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + ID));
    }


   @PostMapping("/registration")
    public User register(@RequestBody User user){
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
