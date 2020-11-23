package com.controller;

import com.config.JwtTokenUtil;
import com.entity.Category;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.UserPayLoad;
import com.repository.BonusRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.UserService;
import com.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    private UserValidator userValidator;
    @Autowired
    UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }


    @GetMapping("/lk")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String currentUsername = request.getRemoteUser();
        User user = userRepository.findByUsername(currentUsername);
        return ResponseEntity.ok(user);
    }


   @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody UserPayLoad userPayLoad, Errors errors){
        userValidator.validate(userPayLoad, errors);
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body("Duplicate");
        }
        User user = new User(userPayLoad.getUsername(),
                userPayLoad.getPassword(),
                userPayLoad.getName(),
                0,
                (long) 1,
                bonusRepository.findById(2));
        userService.save(user);
        return ResponseEntity.ok("Success");
    }


   @PutMapping
    public ResponseEntity<?> modifyProfile(@RequestBody User user, HttpServletRequest request){
       User existingUser = userRepository.findByUsername(request.getRemoteUser());
       existingUser.setName(user.getName());
       userRepository.save(existingUser);
       return ResponseEntity.ok(existingUser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int ID) {
        User existingUser = this.userRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + ID));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok("Success");
    }

}
