package com.paymentproject.domain.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {

    COMMON("common"),
    MERCHANT("merchant");

    private String type;

    public static UserType enumValueOf(String type){
        for(UserType t : UserType.values()){
            if (t.getType().equals(type)){
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code");
    }
}
