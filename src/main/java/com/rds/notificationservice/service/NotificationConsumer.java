package com.rds.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rds.notificationservice.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    @Value("${notification.topic:order.created}")
    private String topic;

    private final Gson gson;


    @KafkaListener(topics = "${notification.topic:order.created}", containerFactory = "kafkaListenerContainerFactory")
    public void onOrderCreated(ConsumerRecord<String, String> rec, Acknowledgment ack){
        try{
            OrderCreatedEvent evt = gson.fromJson(rec.value(), OrderCreatedEvent.class);
            log.info("[Kafka] received {} key={} orderId={}", topic, rec.key(), evt.orderId());

            // send notification
            log.info("[Kafka] sending notification: {}", evt);
            ack.acknowledge();
        }catch(Exception e){
            log.error("[Kafka] processing failed, will NOT ack (record offset stays)", e);
// biarkan tidak ack untuk re-delivery; produksi: pakai DLT / DeadLetterPublishingRecoverer
        }
    }
}