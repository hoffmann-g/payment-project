package com.paymentproject.domain.dtos;

import java.math.BigDecimal;

import com.paymentproject.domain.entities.enums.UserType;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
    
}
