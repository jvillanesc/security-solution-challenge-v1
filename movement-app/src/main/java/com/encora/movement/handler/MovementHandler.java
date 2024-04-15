package com.encora.movement.handler;

import com.encora.movement.exception.BussinesException;
import com.encora.movement.exception.enums.BussinesErrorEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
@Slf4j
public class MovementHandler {

    @Value("${client-credentials.movement-service.client-id}")
    private String depositServiceClientId;


    public Mono<ServerResponse> getMovements(ServerRequest request) {

        ServerRequest.Headers headers = request.headers();
        List<String> authorization = headers.header("Authorization");
        validToken(authorization.get(0));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(Mono.just("detail movements"), String.class)
                );
    }

    private void validToken(String authorization) {
        log.info("authorization {}", authorization);
        String[] tokenParts = authorization.split("\\|");
        String clienId = tokenParts[0];
        String expireAt = tokenParts[2];
        log.info("expireAt {}", expireAt);
        LocalDateTime dateTime = LocalDateTime.parse(expireAt);
        long expireTime = Duration.between(LocalDateTime.now(), dateTime).toSeconds();
        if (!depositServiceClientId.equals(clienId) || expireTime < 0 ) {
            throw new BussinesException(BussinesErrorEnum.TOKEN_INVALID);
        }
    }

}
