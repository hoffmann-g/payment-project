package com.paymentproject.domain.services.exceptions;

public class TransactionValidationException extends RuntimeException {

    public TransactionValidationException(String msg){
        super(msg);
    }
    
}
