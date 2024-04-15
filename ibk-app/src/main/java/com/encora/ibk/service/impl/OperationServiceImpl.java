package com.encora.ibk.service.impl;

import com.encora.ibk.service.OperationService;
import com.encora.ibk.thirdparty.client.ClientCredentialRequest;
import com.encora.ibk.thirdparty.client.TokenClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {

  @Autowired
  @Qualifier("tokenClient")
  private WebClient tokenWebClient;
  @Autowired
  @Qualifier("depositClient")
  private WebClient depositWebClient;
  @Autowired
  @Qualifier("movementClient")
  private WebClient movementWebClient;

  @Autowired
  private ReactiveRedisTemplate<String, TokenClientResponse> reactiveRedisTemplate;

  @Value("${token-client.urls.post-token-url}")
  private String postTokenUrl;
  @Value("${deposit-client.urls.post-deposit-url}")
  private String postDepositUrl;
  @Value("${movement-client.urls.get-movement-url}")
  private String getMovementUrl;

  @Value("${client-credentials.deposit-service.client-id}")
  private String depositServiceClientId;
  @Value("${client-credentials.deposit-service.password}")
  private String depositServicePassword;
  @Value("${client-credentials.movement-service.client-id}")
  private String movementServiceClientId;
  @Value("${client-credentials.movement-service.password}")
  private String movementServicePassword;

  public Mono<String> deposit() {
    return getToken(depositServiceClientId, depositServicePassword)
            .map(tokenClientResponse -> {
              log.info("TOKEN OBTENIDO:{}", tokenClientResponse);
              return tokenClientResponse;
            })
            .flatMap(tokenClientResponse ->
                    depositWebClient.post()
                            .uri(postDepositUrl)
                            .header("Authorization", tokenClientResponse.getAccessToken())
                            .exchange()
                            .flatMap(response -> response.body(BodyExtractors.toMono(String.class))));
  }

  @Override
  public Mono<String> getMovements() {
    return getToken(movementServiceClientId, movementServicePassword)
            .flatMap(tokenClientResponse -> movementWebClient.get()
                    .uri(getMovementUrl)
                    .header("Authorization", tokenClientResponse.getAccessToken())
                    .exchange()
                    .flatMap(response -> response.body(BodyExtractors.toMono(String.class))));
  }

  public Mono<TokenClientResponse> getToken(String clientId, String password) {
    return reactiveRedisTemplate.opsForValue().get(clientId)
            .switchIfEmpty(generateAndSaveToken(clientId, password))
            .flatMap(tokenClientResponse -> generateAndSaveToken(tokenClientResponse, clientId, password));
/*    return generateAndSaveToken(clientId, password);*/
  }

  private boolean isTokenValid(TokenClientResponse tokenClientResponse){
    log.info("VALIDATION TOKEN {} ---- {}", LocalDateTime.now(), tokenClientResponse.getExpireIn());
    long dur = Duration.between(LocalDateTime.now(), tokenClientResponse.getExpireIn()).toSeconds();
    log.info("DURATION {}", dur);
    return dur > 0;
  }

  private Mono<TokenClientResponse> generateAndSaveToken(String clientId, String password){
    log.info("postTokenUrl:{} {} {}", postTokenUrl, clientId, password);
    return this.tokenWebClient.post()
                    .uri(postTokenUrl)
                    .body(BodyInserters.fromObject(
                            ClientCredentialRequest.builder()
                            .clientId(clientId)
                            .password(password)
                            .build()))
            .exchange()
            .flatMap(response -> response.body(BodyExtractors.toMono(TokenClientResponse.class)))
            .flatMap(tokenClientResponse ->
                    reactiveRedisTemplate
                            .opsForValue()
                            .set(clientId, tokenClientResponse)
            )
            .flatMap(booleanMono -> reactiveRedisTemplate.opsForValue().get(clientId));
  }

  private Mono<TokenClientResponse> generateAndSaveToken(TokenClientResponse actualTokenClientResponse, String clientId, String passowrd){
    log.info("token {}", actualTokenClientResponse.getAccessToken());
    if (isTokenValid(actualTokenClientResponse)) {
      return Mono.just(actualTokenClientResponse);
    }
    return generateAndSaveToken(clientId, passowrd);
  }

}
