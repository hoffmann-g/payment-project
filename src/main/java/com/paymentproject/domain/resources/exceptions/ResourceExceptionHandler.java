package com.paymentproject.domain.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paymentproject.domain.services.exceptions.DatabaseException;
import com.paymentproject.domain.services.exceptions.ResourceNotFoundException;
import com.paymentproject.domain.services.exceptions.ServiceStatusException;
import com.paymentproject.domain.services.exceptions.TransactionAuthorizationException;
import com.paymentproject.domain.services.exceptions.TransactionValidationException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        String error = e.getClass().getSimpleName();
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ServiceStatusException.class)
	public ResponseEntity<StandardError> database(ServiceStatusException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TransactionAuthorizationException.class)
	public ResponseEntity<StandardError> database(TransactionAuthorizationException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TransactionValidationException.class)
	public ResponseEntity<StandardError> database(TransactionValidationException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }
    
    
}
