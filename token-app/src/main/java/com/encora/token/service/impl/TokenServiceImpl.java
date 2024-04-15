package com.encora.token.service.impl;

import com.encora.token.exception.BussinesException;
import com.encora.token.exception.enums.BussinesErrorEnum;
import com.encora.token.mapper.TokenMapper;
import com.encora.token.model.domain.ClientCredential;
import com.encora.token.model.domain.Token;
import com.encora.token.repository.CredentialRepository;
import com.encora.token.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

  CredentialRepository credentialRepository;
  TokenMapper tokenMapper;

  public Mono<Token> createToken(ClientCredential clientCredential) {
    log.info("TOKEN Credentials:{} {}", clientCredential.getClientId(), clientCredential.getPassword());
    return credentialRepository.findByClientIdAndPassword(clientCredential.getClientId(), clientCredential.getPassword())
            .switchIfEmpty(Mono.error(new BussinesException(BussinesErrorEnum.BAD_CLIENT_CREDENTIALS)))
            .map(credentialEntity -> {
              LocalDateTime expireAt = LocalDateTime.now().plusSeconds(30);
              log.info("TOKEN expireIn:{}", expireAt);
              return Token.builder()
                      .accessToken(credentialEntity.getClientId() + "|" + UUID.randomUUID().toString() + "|" + expireAt)
                      .tokenType("Bearer")
                      .expireIn(expireAt)
                      .build();
            });
  }

}
