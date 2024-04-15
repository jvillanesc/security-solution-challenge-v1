package com.encora.ibk.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean("tokenClient")
    @Description("token web client")
    public WebClient tokenClient(@Value("${token-client.urls.base-url}") String serviceUrl){
        return WebClient.builder()
                .baseUrl(serviceUrl)
                .build() ;
    }

    @Bean("depositClient")
    @Description("deposit web client")
    public WebClient depositClient(@Value("${deposit-client.urls.base-url}") String serviceUrl){
        return WebClient.builder()
                .baseUrl(serviceUrl)
                .build() ;
    }

    @Bean("movementClient")
    @Description("movement web client")
    public WebClient movementClient(@Value("${movement-client.urls.base-url}") String serviceUrl){
        return WebClient.builder()
                .baseUrl(serviceUrl)
                .build() ;
    }

}
