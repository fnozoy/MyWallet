package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.model.entity.User;

import java.util.Optional;

public interface UserService {

    UserDTO authenticate(UserDTO userDTO);
    UserDTO signupUser(UserDTO userDTO);

    void validateSingleEmail(String email);

    Optional<User> findById(Long id);

}
