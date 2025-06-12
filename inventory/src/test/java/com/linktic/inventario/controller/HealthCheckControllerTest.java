package com.linktic.inventario.controller;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HealthCheckControllerTest {

    @Test
    void testHealthReturnsUpStatusAndTimestamp() {
        // Arrange
        HealthCheckController controller = new HealthCheckController();

        // Act
        Map<String, Object> result = controller.health();

        // Assert
        assertNotNull(result);
        assertEquals("UP", result.get("status"));
        assertNotNull(result.get("timestamp"));
        assertTrue(result.get("timestamp") instanceof String);
    }
}