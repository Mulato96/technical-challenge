package com.linktic.inventario.kafka;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryEventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "inventory-events";

    public void publishInventoryChange(Long productId, Integer newQuantity) {
        String message = String.format("{\"productId\":%d,\"newQuantity\":%d}", productId, newQuantity);
        kafkaTemplate.send(TOPIC, message);
        logger.info("Event published to Kafka: {}", message);
    }
}
