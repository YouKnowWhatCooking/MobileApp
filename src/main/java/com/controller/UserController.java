package com.controller;

import com.entity.Bonus;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.Response;
import com.payload.UserPayLoad;
import com.repository.BonusRepository;
import com.repository.UserRepository;
import com.service.UserService;
import com.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }


   @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody UserPayLoad userPayLoad, Errors errors){
        userValidator.validate(userPayLoad, errors);
        if(errors.hasErrors()){
            System.out.println(errors);
            return ResponseEntity.badRequest().body("Пользователь с таким именем уже существует");
        }

       Bonus bonus = bonusRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Bonus not found"));
        User user = new User(userPayLoad.getUsername(),
                userPayLoad.getPassword(),
                userPayLoad.getName(),
                0,
                (long) 1,
                bonus);
        userService.save(user);
        return ResponseEntity.ok().body("Вы были успешно зарегистрированы в системе");
    }


   @PutMapping
    public ResponseEntity<?> modifyProfile(@RequestBody User user, HttpServletRequest request){
        User existingUser = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setName(user.getName());
        userRepository.save(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    @PostMapping("/password")
    public ResponseEntity<Response> passwordRecovery(@RequestBody User user){
        try{
            User existingUser = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            if (!user.getName().equals(existingUser.getName())){
                return ResponseEntity.badRequest().build();
            }
            String password = "";
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            encoder.matches(password, existingUser.getPassword());
            Response response = new Response(password);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().build();
        } catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int ID) {
        User existingUser = this.userRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + ID));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }

}
