package com.encora.token.handlers.client;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientCredentialRequest {

  @Size(max = 9)
  private String clientId;
  private String password;

}
