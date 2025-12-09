package com.example.userservice.producer;

import com.example.userservice.config.DirectExchangeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
@RequiredArgsConstructor
public class Producer1 {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(DirectExchangeConfig.EXCHANGE, DirectExchangeConfig.ROUTING_KEY, message);
    }
}
