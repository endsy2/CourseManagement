package com.example.userservice.service.implement;

import com.example.userservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisImpl implements RedisService {
    private final StringRedisTemplate redisTemplate;

    @Override
    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }
    @Override
    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
