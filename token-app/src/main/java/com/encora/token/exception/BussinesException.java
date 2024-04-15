package com.encora.token.exception;

import com.encora.token.exception.enums.BussinesErrorEnum;

public class BussinesException extends HiperiumException {

    public BussinesException(BussinesErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getMessage());
    }
}
