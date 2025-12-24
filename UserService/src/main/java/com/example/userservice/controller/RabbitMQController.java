package com.example.userservice.controller;

import com.example.userservice.producer.FanoutProducer;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RabbitMQController {
    @Autowired
    private FanoutProducer fanoutProducer;
    @GetMapping("/testRabitt")
    public void sendMessage(@RequestParam String message) {
        fanoutProducer.sendMessage(message);

    }


}
