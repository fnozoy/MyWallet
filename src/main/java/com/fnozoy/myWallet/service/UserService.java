package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.model.entity.User;

import java.util.Optional;

public interface UserService {

    User authenticate(String email, String password);
    User signupUser(User mwm001user);

    void validateSingleEmail(String email);

    Optional<User> findById(Long id);

}
