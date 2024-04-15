package com.encora.ibk.handler;

import com.encora.ibk.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
@AllArgsConstructor
public class AccountHandler {

    OperationService operationService;

    public Mono<ServerResponse> deposit(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(operationService.deposit(), String.class)
                );
    }

    public Mono<ServerResponse> getMovements(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(operationService.getMovements(), String.class)
                );
    }
}
