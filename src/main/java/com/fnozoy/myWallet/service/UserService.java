package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.model.entity.User;

public interface UserService {

    User autenticate(String email, String password);
    User signupUser(User mwm001user);

    void validateSingleEmail(String email);

}
