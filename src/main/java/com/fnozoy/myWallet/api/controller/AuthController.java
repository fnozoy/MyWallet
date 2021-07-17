package com.fnozoy.myWallet.api.controller;


import com.fnozoy.myWallet.api.dto.LoginDTO;
import com.fnozoy.myWallet.api.dto.TokenDto;
import com.fnozoy.myWallet.config.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/api/token/v1/auth")
    public ResponseEntity authenticate(@RequestBody LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken loginData = loginDTO.convert();

        try {
            Authentication authentication = authManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
