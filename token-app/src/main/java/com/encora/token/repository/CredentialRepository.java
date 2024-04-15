package com.encora.token.repository;

import com.encora.token.repository.entity.CredentialEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CredentialRepository extends R2dbcRepository<CredentialEntity, Integer>{

    Mono<CredentialEntity> findByClientIdAndPassword(String clientId, String password);

}
