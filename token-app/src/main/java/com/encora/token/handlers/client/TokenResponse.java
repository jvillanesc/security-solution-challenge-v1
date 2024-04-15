package com.encora.token.handlers.client;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TokenResponse {

  private String accessToken;
  private String tokenType;
  private LocalDateTime expireIn;

}
