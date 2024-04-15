package com.encora.deposit.configuration;

import com.encora.deposit.dto.ErrorDetailsDto;
import com.encora.deposit.exception.BussinesException;
import com.encora.deposit.handler.DepositHandler;
import com.encora.deposit.util.ErrorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class DepositRouter {

    @Bean
    public RouterFunction<ServerResponse> tokenRouters(DepositHandler depositHandler) {
        return RouterFunctions.route()
                .path("/api", builder -> builder
                        .POST("/deposits", accept(MediaType.APPLICATION_JSON), depositHandler::deposit))
                .build();
   }

    @Bean
    WebFilter exceptionHandler() {
        return (exchange, next) -> next.filter(exchange)
                .onErrorResume(BussinesException.class, e -> {

                    ErrorDetailsDto errorDetails = ErrorUtil.getErrorDetailsVO(
                            exchange,
                            e.getErrorMessageKey(),
                            e.getErrorCode());

                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.BAD_REQUEST);
                    DataBuffer buffer = response.bufferFactory().wrap(serialize(errorDetails));
                    return response.writeWith(Flux.just(buffer));
                });
    }

    static byte[] serialize(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
