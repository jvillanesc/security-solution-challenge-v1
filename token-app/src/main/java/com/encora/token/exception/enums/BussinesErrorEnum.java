package com.encora.token.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BussinesErrorEnum {

    BAD_CLIENT_CREDENTIALS("ERR-BS-001", "Client not exist or bad client password");

    private final String code;
    private final String message;

}
