package com.maddy8381.PersonalProjectMngmTool.services;

import com.maddy8381.PersonalProjectMngmTool.domain.User;
import com.maddy8381.PersonalProjectMngmTool.exceptions.UserNameAlreadyExistsException;
import com.maddy8381.PersonalProjectMngmTool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //for encoding password

    public User saveUser(User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //Username must be unique
            newUser.setUsername(newUser.getUsername()); //Incase username exists then throw UserNameAlreadyExistsException

            //We dont persist or show confirm password
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UserNameAlreadyExistsException("Username "+ newUser.getUsername() + " already exists");
        }
    }

}
