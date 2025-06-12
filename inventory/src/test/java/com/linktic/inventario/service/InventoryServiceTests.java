package com.linktic.inventario.service;

import com.linktic.inventario.dto.InventoryRequest;
import com.linktic.inventario.dto.InventoryResponse;
import com.linktic.inventario.dto.PurchaseRequest;
import com.linktic.inventario.entity.Inventory;
import com.linktic.inventario.repository.InventoryRepository;
import com.linktic.inventario.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTests {

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    void testGetInventoryByProductId() {
        Inventory inventory = new Inventory(1L, 100);
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        InventoryResponse response = inventoryService.getStock(1L);
        assertEquals(100, response.getQuantity());
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = new Inventory(1L, 100);
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        InventoryResponse response = inventoryService.updateStock(1L, new InventoryRequest(50));
        assertEquals(50, response.getQuantity());
    }

    @Test
    void testPurchase() {
        Inventory inventory = new Inventory(1L, 100);
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        InventoryResponse response = inventoryService.purchaseProduct(new PurchaseRequest(1L, 10));
        assertEquals(90, response.getQuantity());
    }

    @Test
    void testGetInventoryByProductId_NotFound() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inventoryService.getStock(1L));
    }
}
