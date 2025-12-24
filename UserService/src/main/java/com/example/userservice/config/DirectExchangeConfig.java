//package com.example.userservice.config;
//
//
//
//@Configuration
//public class DirectExchangeConfig {
//
//    @Bean
//    public Queue directQueue() {
//        return new Queue("direct.queue");
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange("direct.exchange");
//    }
//
//    @Bean
//    public Binding directBinding() {
//        return BindingBuilder
//                .bind(directQueue())
//                .to(directExchange())
//                .with("direct.key");
//    }
//}
