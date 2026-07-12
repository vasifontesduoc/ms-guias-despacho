package com.duoc.ms_guias_despacho.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.duoc.ms_guias_despacho.config.RabbitMQConfig;
import com.duoc.ms_guias_despacho.entity.GuiaDespacho;

@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(GuiaDespacho guiaDespacho) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.MAIN_EXCHANGE,
                "",
                guiaDespacho);

        System.out.println("Mensaje enviado a RabbitMQ");
    }
}
