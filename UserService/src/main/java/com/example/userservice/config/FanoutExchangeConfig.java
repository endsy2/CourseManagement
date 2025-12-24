//package com.example.userservice.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configurationlass FanoutExchangeConfig {
//
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanout.exchange",true,false);
//    }
//
//    @Bean
//    public Queue fanoutQueue1() {
//        return new Queue("fanout.queue", true);
//    }
//
//    @Bean
//    public Queue fanoutQueue2() {
//        return new Queue("fanout.queue", true);
//    }
//
//    @Bean
//    public Binding binding1(FanoutExchange fanoutExchange, Queue fanoutQueue1) {
//        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
//    }
//
//    @Bean
//    public Binding binding2(FanoutExchange fanoutExchange, Queue fanoutQueue2) {
//        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
//    }
//}
