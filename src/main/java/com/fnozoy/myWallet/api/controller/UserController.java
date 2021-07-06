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
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .pswd(userDTO.getPswd())
                .build();
        try {
            User userAuthenticated = userService.authenticate(userDTO.getEmail(), userDTO.getPswd());
            return new ResponseEntity(userAuthenticated, HttpStatus.OK);
        } catch (AutenticationErrorException e){
            return ResponseEntity.badRequest().body("User cannot sign in. " + e.getMessage());
        }

    }

    @PostMapping("/api/v1/user/signup")
    public ResponseEntity signup(@RequestBody UserDTO userDTO){
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .pswd(userDTO.getPswd())
                .build();

        try {
            User userSignedup = userService.signupUser(user);
            return new ResponseEntity(userDTO, HttpStatus.CREATED);
        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body("User cannot signup. " + e.getMessage());
        }

    }

    @GetMapping("/api/v1/user/getbalance/{id}")
    public ResponseEntity getBalance (@PathVariable Long id){

        Optional<User> user = userService.findById(id);
        if (!user.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal balance = entryService.getBalanceByUserId(id);
        return new ResponseEntity(balance, HttpStatus.OK);

    }

}
