package com.encora.movement.exception.handler;

import com.encora.movement.dto.ErrorDetailsDto;
import com.encora.movement.exception.BussinesException;
import com.encora.movement.util.ErrorUtil;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BussinesException.class)
    public final Mono<ResponseEntity<ErrorDetailsDto>> handleBussinesException(
            BussinesException exception,
            ServerWebExchange exchange) {
        ErrorDetailsDto errorDetails = ErrorUtil.getErrorDetailsVO(
                exchange,
                exception.getErrorMessageKey(),
                exception.getErrorCode());
        super.logger.error("handleBussinesException(): " + errorDetails);
        return Mono.just(new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Object>> handleWebExchangeBindException(
            WebExchangeBindException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            ServerWebExchange exchange) {
        String errorMessage = null;
        FieldError fieldError = exception.getFieldError();
        if (Objects.nonNull(fieldError)) {
            String messageKey = fieldError.getDefaultMessage();
            errorMessage = messageKey;
        }
        if (Objects.isNull(errorMessage)) {
            errorMessage = exception.getMessage();
        }
        ErrorDetailsDto errorDetails = ErrorUtil.getErrorDetailsVO(exchange, errorMessage, "ERR-FLD-000");
        super.logger.error("handleWebExchangeBindException(): " + errorDetails);
        return Mono.just(new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST));
    }
}