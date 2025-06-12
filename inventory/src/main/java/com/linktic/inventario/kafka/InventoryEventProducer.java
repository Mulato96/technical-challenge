package com.linktic.inventario.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "inventory-events";

    public void publishInventoryChange(Long productId, Integer newQuantity) {
        String message = String.format("{\"productId\":%d,\"newQuantity\":%d}", productId, newQuantity);
        kafkaTemplate.send(TOPIC, message);
        log.info("Event published to Kafka: {}", message);
    }
}
