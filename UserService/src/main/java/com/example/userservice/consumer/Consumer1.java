package com.example.userservice.consumer;

import com.example.userservice.config.DirectExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Consumer1 {

    @RabbitListener(queues = DirectExchangeConfig.QUEUE)
    public void receiveMessage(String message) {
        log.debug("Received Message from DirectExchange "+message);
//        return message;
    }

}
