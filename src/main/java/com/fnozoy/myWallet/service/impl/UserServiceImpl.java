package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AutenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
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
    public UserDTO authenticate(UserDTO userDTO) {

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow( () -> new AutenticationErrorException("Email not found"));

        if (!user.getPswd().equals(userDTO.getPswd())){
            throw new AutenticationErrorException("Password invalid.");
        }

        return userDTO;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserDTO signupUser(UserDTO userDTO) {

        validateSingleEmail(userDTO.getEmail());
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .pswd(userDTO.getPswd())
                .build();
        user = userRepository.save(user);
        userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .pswd(user.getPswd())
                .build();
        return userDTO;
    }

    @Override
    public void validateSingleEmail(String email) {

        if (userRepository.existsByEmail(email)){
            throw new BusinessRuleException("Email already exists.");
        }

    }
}
