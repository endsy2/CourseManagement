//package com.example.userservice.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TopicExchangeConfig {
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange("topic.exchange");
//    }
//
//    @Bean
//    public Queue orderQueue() {
//        return new Queue("order.queue");
//    }
//
//    @Bean
//    public Queue paymentQueue() {
//        return new Queue("payment.queue");
//    }
//
//    @Bean
//    public Binding orderBinding() {
//        return BindingBuilder
//                .bind(orderQueue())
//                .to(topicExchange())
//                .with("order.*");
//    }
//
//    @Bean
//    public Binding paymentBinding() {
//        return BindingBuilder
//                .bind(paymentQueue())
//                .to(topicExchange())                .with("*.created");
//    }
//}
