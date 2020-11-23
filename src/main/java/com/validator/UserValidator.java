package com.validator;

import com.entity.Bonus;
import com.entity.User;
import com.payload.UserPayLoad;
import com.repository.BonusRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if (userRepository.findByUsername(userPayLoad.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.user.username");
        }
    }

}
