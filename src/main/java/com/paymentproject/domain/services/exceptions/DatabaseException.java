package com.paymentproject.domain.services.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(Object object){
        super("Database integrity violated. ID: " + object);
    }
    
}
