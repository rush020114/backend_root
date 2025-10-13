package com.green.backend_root.counter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  /**
   * RedisTemplate Bean 설정
   * Redis에 데이터를 저장하고 조회할 때 사용하는 핵심 객체
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();

    // Redis 연결 설정
    template.setConnectionFactory(connectionFactory);

    // Key와 Value를 String 형태로 직렬화 (Redis에 저장될 때 문자열로 저장됨)
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());

    return template;
  }
}

