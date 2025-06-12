package com.linktic.inventario.kafka;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

public class InventoryEventProducerTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private InventoryEventProducer producer;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        producer = new InventoryEventProducer(kafkaTemplate);
    }

    @Test
    void testPublishInventoryChange_sendsCorrectMessage() {
        // Arrange
        Long productId = 123L;
        Integer newQuantity = 10;
        String expectedMessage = "{\"productId\":123,\"newQuantity\":10}";

        // Act
        producer.publishInventoryChange(productId, newQuantity);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq("inventory-events"), eq(expectedMessage));
    }
}
