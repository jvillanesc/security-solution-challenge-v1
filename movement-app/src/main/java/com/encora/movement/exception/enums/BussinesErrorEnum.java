package com.encora.movement.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BussinesErrorEnum {

    TOKEN_INVALID("ERR-BS-001", "Token invalid");

    private final String code;
    private final String message;

}
