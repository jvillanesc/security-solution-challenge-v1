package com.encora.token.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientCredential {

  private String clientId;
  private String password;

}
