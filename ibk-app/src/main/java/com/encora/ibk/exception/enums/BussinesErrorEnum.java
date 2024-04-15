package com.encora.ibk.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BussinesErrorEnum {

    STARSHIP_NOT_EXIST("ERR-BS-001", "Starship not exist"),
    STARSHIP_EXPIRED("ERR-BS-002", "Starship expired"),
    STARSHIPS_SOURCE_NOT_AVAILABLE("ERR-BS-003", "Startship source not available");

    private final String code;
    private final String message;

}
