package com.example.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service",url = "http://localhost:8100")
public interface StoreClients {
//    @PostMapping("/createStudent")
//    String createStudent(@RequestBody)
}
