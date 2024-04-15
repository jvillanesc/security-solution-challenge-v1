package com.encora.ibk.service;


import reactor.core.publisher.Mono;

public interface OperationService {

  Mono<String> deposit();

  Mono<String> getMovements();
}
