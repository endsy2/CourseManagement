package com.example.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {
    public static final String EXCHANGE="directExchange";
    public static final String QUEUE="directQueue";
    public static final String ROUTING_KEY="directRoutingKey";

    @Bean
    public Queue directQueue(){
        return new Queue(QUEUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key.#");
    }

}
