package com.example.userservice.service.implement;

import com.example.userservice.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshtokenImpl implements RefreshTokenService {

    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public void saveRefreshToken(String username, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(refreshToken,username, Duration.ofSeconds(duration));
    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken, String username) {
        log.debug("username={}",username);
        String key = "refreshToken:" + username;
        log.debug("key:{}",key);
        String tokenInRedis = redisTemplate.opsForValue().get(key);
        log.debug("tokenInRedis:{}",tokenInRedis);
        return refreshToken.equals(tokenInRedis);
    }

    public void deleteRefreshToken(String username) {
        String keyRefreshToken="refreshToken:" + username;
        String keyAccessToken="accessToken:" + username;
        List<String> token=List.of(keyRefreshToken,keyAccessToken);
        redisTemplate.delete(token);
    }
}
