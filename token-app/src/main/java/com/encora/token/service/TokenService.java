package com.encora.token.service;

import com.encora.token.model.domain.ClientCredential;
import com.encora.token.model.domain.Token;
import reactor.core.publisher.Mono;

public interface TokenService {

  public Mono<Token> createToken(ClientCredential clientCredential);

}
