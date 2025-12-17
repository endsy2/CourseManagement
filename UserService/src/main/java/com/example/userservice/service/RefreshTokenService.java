package com.example.userservice.service;

public interface RefreshTokenService {
    public void saveRefreshToken(String username,String refreshToken,long duration);
    public boolean isRefreshTokenValid(String refreshToken,String username);
    public void deleteRefreshToken(String username);
}
