package com.paymentproject.domain.services.exceptions;

public class TransactionAuthorizationException extends RuntimeException{

    public TransactionAuthorizationException(String msg){
        super(msg);
    }
    
}
