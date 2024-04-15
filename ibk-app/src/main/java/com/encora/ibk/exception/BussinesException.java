package com.encora.ibk.exception;

import com.encora.ibk.exception.enums.BussinesErrorEnum;

public class BussinesException extends HiperiumException {

    public BussinesException(BussinesErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getMessage());
    }
}
