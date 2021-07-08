package com.fnozoy.myWallet.exceptions;

public class AuthenticationErrorException extends RuntimeException{

    public AuthenticationErrorException(String msg){
        super(msg);
    }
}
