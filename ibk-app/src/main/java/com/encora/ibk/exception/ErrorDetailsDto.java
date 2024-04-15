package com.encora.ibk.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetailsDto {
    public final String errorDate;
    public final String requestedPath;
    public final String errorMessage;
    public final String errorCode;
}
