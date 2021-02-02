package com.maddy8381.PersonalProjectMngmTool.validator;

import com.maddy8381.PersonalProjectMngmTool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass); //Support USer Class
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        if(user.getPassword().length() < 6) {
            errors.rejectValue("password", "Length","Password Must be at least 6 Characters");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "Match","Password must match");
        }
    }
}
