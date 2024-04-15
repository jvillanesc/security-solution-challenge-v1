package com.encora.ibk.thirdparty.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClientCredentialRequest {

  private String clientId;
  private String password;

}
