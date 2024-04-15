package com.encora.token.handlers;

import com.encora.token.handlers.client.ClientCredentialRequest;
import com.encora.token.handlers.client.TokenResponse;
import com.encora.token.mapper.TokenMapper;
import com.encora.token.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
@AllArgsConstructor
@Slf4j
public class TokenHandler {

    TokenService tokenService;
    TokenMapper tokenMapper;

    public Mono<ServerResponse> createToken(ServerRequest request) {
        log.info("TOKE createToken:");
        Mono<ClientCredentialRequest> clientCredentialRequestMono = request.bodyToMono(ClientCredentialRequest.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(clientCredentialRequestMono
                        .flatMap(
                        clientCredentialRequest -> tokenService.createToken(
                                tokenMapper.mapClientCredential(clientCredentialRequest)
                        ).map(tokenMapper::mapTokenResponse)), TokenResponse.class)
                );
    }
}
