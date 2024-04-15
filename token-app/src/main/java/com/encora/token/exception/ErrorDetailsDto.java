package com.encora.token.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ErrorDetailsDto implements Serializable {
    public final String errorDate;
    public final String requestedPath;
    public final String errorMessage;
    public final String errorCode;
}
