package com.paymentproject.domain.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {

    COMMON(1),
    MERCHANT(2);

    private int type;
}
