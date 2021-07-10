package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AuthenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO authenticate(UserDTO userDTO) {

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow( () -> new AuthenticationErrorException("Email not found"));

        if (!user.getPswd().equals(userDTO.getPswd())){
            throw new AuthenticationErrorException("Password invalid.");
        }

        userDTO = userToDTO(user);

        return userDTO;
    }

    public UserDTO userToDTO(User user){
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
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
