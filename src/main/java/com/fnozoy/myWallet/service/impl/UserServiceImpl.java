package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User autenticate(String email, String password) {
        return null;
    }

    @Override
    public User signupUser(User mwm001user) {
        return null;
    }

    @Override
    public void validateSingleEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        if (exists){
            throw new BusinessRuleException("Informed email already exists.");
        }
    }
}
