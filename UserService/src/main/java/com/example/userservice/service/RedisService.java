package com.example.userservice.service;

public interface RedisService {
    public void setKey(String key,String value);
    public String getKey(String key);
    public void deleteKey(String key);
}
