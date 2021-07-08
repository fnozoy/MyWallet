package com.fnozoy.myWallet.api.controller;

import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AutenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.service.EntryService;
import com.fnozoy.myWallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    private UserService userService;
    private EntryService entryService;

    public UserController(UserService userService, EntryService entryService) {
        this.userService = userService;
        this.entryService = entryService;
    }

    @PostMapping("/api/v1/user/authenticate")
    public ResponseEntity authenticate(@RequestBody UserDTO userDTO){
        try {

            UserDTO userAuthenticated = userService.authenticate(userDTO);
            return new ResponseEntity(userAuthenticated, HttpStatus.OK);

        } catch (AutenticationErrorException e){
            return ResponseEntity.badRequest().body("User cannot sign in. " + e.getMessage());
        }
    }

    @PostMapping("/api/v1/user/signup")
    public ResponseEntity signup(@RequestBody UserDTO userDTO){
        try {

            UserDTO userSignedUp = userService.signupUser(userDTO);
            return new ResponseEntity(userSignedUp, HttpStatus.CREATED);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body("User cannot signup. " + e.getMessage());
        }

    }

}
