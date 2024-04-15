package com.encora.movement.exception;

import com.encora.movement.exception.enums.BussinesErrorEnum;

public class BussinesException extends HiperiumException {

    public BussinesException(BussinesErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getMessage());
    }
}
