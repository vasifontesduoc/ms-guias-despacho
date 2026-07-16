package com.duoc.ms_guias_despacho.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Cola principal
    public static final String MAIN_QUEUE = "guias-despacho.queue";
    public static final String MAIN_EXCHANGE = "guias-despacho.exchange";

    // Dead Letter Queue
    public static final String DLQ = "guias-despacho.dlq";
    public static final String DLX = "guias-despacho.dlx";
    public static final String DLQ_ROUTING_KEY = "dlq";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {

        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);

        return factory;
    }

    // ==========================
    // Cola principal
    // ==========================

    @Bean
    public Queue myQueue() {

        Map<String, Object> args = new HashMap<>();

        args.put("x-dead-letter-exchange", DLX);
        args.put("x-dead-letter-routing-key", DLQ_ROUTING_KEY);

        return new Queue(MAIN_QUEUE, true, false, false, args);
    }

    @Bean
    public DirectExchange myExchange() {

        return new DirectExchange(MAIN_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue myQueue, DirectExchange myExchange) {

        return BindingBuilder
                .bind(myQueue)
                .to(myExchange)
                .with("");
    }

    // ==========================
    // Dead Letter Queue
    // ==========================

    @Bean
    public Queue deadLetterQueue() {

        return new Queue(DLQ, true);
    }

    @Bean
    public DirectExchange deadLetterExchange() {

        return new DirectExchange(DLX);
    }

    @Bean
    public Binding deadLetterBinding() {

        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ_ROUTING_KEY);
    }

    // ==========================
    // RabbitTemplate
    // ==========================

    @Bean
    public RabbitTemplate rabbitTemplate(
            CachingConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }
}
