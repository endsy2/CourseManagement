package com.example.apigateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/user-service/**")
    public ResponseEntity<String> userService(HttpServletRequest request, @RequestBody(required = false) String body) {
        String path = request.getRequestURI().replace("/user-service", "");
        return forward("http://localhost:8080" + path, request, body);
    }

    @RequestMapping("/course-service/**")
    public ResponseEntity<String> courseService(HttpServletRequest request, @RequestBody(required = false) String body) {
        String path = request.getRequestURI().replace("/course-service", "");
        return forward("http://localhost:8090" + path, request, body);
    }

    private ResponseEntity<String> forward(String url, HttpServletRequest request, String body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            request.getHeaderNames().asIterator().forEachRemaining(name ->
                headers.add(name, request.getHeader(name))
            );
            if (request.getQueryString() != null) {
                url = url + "?" + request.getQueryString();
            }
            log.info("Forwarding to: {}", url);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange(url, HttpMethod.valueOf(request.getMethod()), entity, String.class);
        } catch (Exception e) {
            log.error("Proxy error: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
