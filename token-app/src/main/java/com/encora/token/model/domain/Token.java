package com.encora.token.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Token {

  private String accessToken;
  private String tokenType;
  private LocalDateTime expireIn;

  public TokenBuilder mutate() {
    return Token.builder()
            .accessToken(accessToken)
            .tokenType(tokenType)
            .expireIn(expireIn);
  }

}
