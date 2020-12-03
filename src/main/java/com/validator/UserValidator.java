package com.validator;

import com.entity.Bonus;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.UserPayLoad;
import com.repository.BonusRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserPayLoad userPayLoad = (UserPayLoad) o;
        boolean existingUser = userRepository.findByUsername(userPayLoad.getUsername())
                .isPresent();

        if (existingUser) {
            System.out.println(existingUser);
            errors.rejectValue("username", "Duplicate.user.username");
        }
    }

}
