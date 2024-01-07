package com.paymentproject.domain.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String msg){
        super("Could not find specified resource. " + msg);
    }
}
