package com.fnozoy.myWallet.exceptions;

public class AutenticationErrorException extends RuntimeException{

    public AutenticationErrorException(String msg){
        super(msg);
    }
}
