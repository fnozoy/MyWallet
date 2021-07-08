package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.api.dto.UserDTO;

public interface UserService {

    UserDTO authenticate(UserDTO userDTO);
    UserDTO signupUser(UserDTO userDTO);
    void validateSingleEmail(String email);

}
