package com.paymentproject.domain.services.exceptions;

public class ServiceStatusException extends RuntimeException {

    public ServiceStatusException(String msg){
        super("Server is currently not working. " + msg);
    }
    
}
