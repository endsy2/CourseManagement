package com.example.courseservice.consumer;

import com.example.courseservice.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME2)
    public void listen2(String message) {
        log.debug("Received message: " + message);
    }
}