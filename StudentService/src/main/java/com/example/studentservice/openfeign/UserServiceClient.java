package com.example.studentservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="USER-SERVICE")
public interface UserServiceClient {
}
