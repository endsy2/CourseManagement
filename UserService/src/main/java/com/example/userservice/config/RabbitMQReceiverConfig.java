package com.example.userservice.config;//package com.example.userservice.config;
//
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQReceiverConfig {
//
//    public static final String QUEUE_NAME = "testingqueue";
//    public static final String EXCHANGE_NAME = "myExchange";
//
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME, false);
//    }
//
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        // Binding with routing key pattern
//        return BindingBuilder.bind(queue).to(exchange).with("routing.key.#");
//    }
//}
