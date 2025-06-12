package com.linktic.inventario.controller;

import com.linktic.inventario.dto.InventoryRequest;
import com.linktic.inventario.dto.InventoryResponse;
import com.linktic.inventario.dto.PurchaseRequest;
import com.linktic.inventario.service.InventoryService;
import com.linktic.inventario.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
@Import(InventoryControllerTest.MockConfig.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryService inventoryService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public InventoryService productService() {
            return Mockito.mock(InventoryServiceImpl.class);
        }
    }

    @Test
    void testGetInventoryByProductId() throws Exception {
        InventoryResponse response = new InventoryResponse(1L, 100);
        Mockito.when(inventoryService.getStock(1L)).thenReturn(response);

        mockMvc.perform(get("/api/inventory/1") .header("X-API-KEY", "linktic2025*_"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.quantity").value(100));
    }

    @Test
    void testUpdateInventory() throws Exception {
        InventoryRequest request = new InventoryRequest(50);
        InventoryResponse response = new InventoryResponse(1L, 50);
        Mockito.when(inventoryService.updateStock(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":50}") .header("X-API-KEY", "linktic2025*_"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.quantity").value(50));
    }

    @Test
    void testPurchase() throws Exception {
        PurchaseRequest request = new PurchaseRequest(1L, 10);
        InventoryResponse response = new InventoryResponse(1L, 90);
        Mockito.when(inventoryService.purchaseProduct(any())).thenReturn(response);

        mockMvc.perform(post("/api/inventory/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":10}") .header("X-API-KEY", "linktic2025*_"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.quantity").value(90));
    }
}

