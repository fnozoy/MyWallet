package com.fnozoy.myWallet.api.controller;

import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AuthenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user/v1/authenticate")
    public ResponseEntity authenticate(@RequestBody UserDTO userDTO){
        try {

            UserDTO userAuthenticated = userService.authenticate(userDTO);
            return new ResponseEntity(userAuthenticated, HttpStatus.OK);

        } catch (AuthenticationErrorException e){
            return ResponseEntity.badRequest().body("User cannot sign in. " + e.getMessage());
        }
    }

    @PostMapping("/api/user/v1/signup")
    public ResponseEntity signup(@RequestBody UserDTO userDTO){
        try {

            UserDTO userSignedUp = userService.signupUser(userDTO);
            return new ResponseEntity(userSignedUp, HttpStatus.CREATED);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body("User cannot signup. " + e.getMessage());
        }

    }

}
