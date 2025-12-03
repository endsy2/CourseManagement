package com.example.courseservice.client;

import com.example.courseservice.Dto.mapping.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service",url="http://localhost:8080")
public interface UserServiceClient {
    @GetMapping("user/getById")
    ResponseEntity<String> getbyid();
    @GetMapping("user/getUserByName")
    UserDTO getUserByName(@RequestParam("name") String name);
}
