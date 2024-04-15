package com.encora.token.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@Table("credential")
public class CredentialEntity {

  @Id
  @Column("credential_id")
  private Integer credenialId;
  @Column("client_id")
  private String clientId;
  @Column("password")
  private String password;

}
