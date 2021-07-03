package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.exceptions.AutenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()){
            throw new AutenticationErrorException("Email not found.");
        }

        if (!user.get().getPswd().equals(password)){
            throw new AutenticationErrorException("Email or password invalid.");
        }

        return user.get();
    }

    @Override
    @Transactional
    public User signupUser(User user) {
        validateSingleEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void validateSingleEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        if (exists){
            throw new BusinessRuleException("Informed email already exists.");
        }
    }
}
