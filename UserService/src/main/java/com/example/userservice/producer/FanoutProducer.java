package com.example.userservice.producer;


import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FanoutProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    public void sendMessage(String message) {
        // Routing key is ignored in fanout exchange
        amqpTemplate.convertAndSend("fanout.exchange", "", message);
        System.out.println("Sent: " + message);
    }
}
