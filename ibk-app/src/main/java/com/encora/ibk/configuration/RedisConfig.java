package com.encora.ibk.configuration;


import com.encora.ibk.thirdparty.client.TokenClientResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, TokenClientResponse> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<TokenClientResponse> serializer = new Jackson2JsonRedisSerializer<>(TokenClientResponse.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, TokenClientResponse> builder =RedisSerializationContext.newSerializationContext(new Jackson2JsonRedisSerializer<>(String.class));
        RedisSerializationContext<String, TokenClientResponse> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}
