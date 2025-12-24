//package com.example.userservice.consumer;
//
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
////@RabbitListener(queues="fanout.queue2")
//public class FanoutConsumer2 {
//
//    @RabbitHandler
//    public void receive(String message) {
//        System.out.println("FanoutConsumer2 received: " + message);
//    }
//}
