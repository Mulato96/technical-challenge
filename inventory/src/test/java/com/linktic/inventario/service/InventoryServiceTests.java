package com.linktic.inventario.service;

import com.linktic.inventario.dto.*;
import com.linktic.inventario.entity.Inventory;
import com.linktic.inventario.entity.PurchaseHistoryEntity;
import com.linktic.inventario.kafka.InventoryEventProducer;
import com.linktic.inventario.repository.InventoryRepository;
import com.linktic.inventario.repository.PurchaseHistoryRepository;
import com.linktic.inventario.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTests {

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Mock
    private InventoryEventProducer inventoryEventProducer;

    @Test
    void testGetInventoryByProductId() {
        Inventory inventory = new Inventory(1L,1L, 100);
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

        InventoryResponse response = inventoryService.getStock(1L);
        assertEquals(100, response.getQuantity());
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = new Inventory(1L,1L, 100);
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        InventoryResponse response = inventoryService.updateStock(1L, new InventoryRequest(50));
        assertEquals(50, response.getQuantity());
    }

    @Test
    void testCreateInventory() {
        CreateInventoryRequest request = new CreateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(10);

        Inventory savedInventory = new Inventory();
        savedInventory.setId(100L);
        savedInventory.setProductId(1L);
        savedInventory.setQuantity(10);

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(savedInventory);

        CreateInventoryResponse response = inventoryService.saveStock(request);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(1L, response.getProductId());
        assertEquals(10, response.getQuantity());

        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository, times(1)).save(captor.capture());

        Inventory captured = captor.getValue();
        assertEquals(1L, captured.getProductId());
        assertEquals(10, captured.getQuantity());
    }

    @Test
    void testPurchase() {
        Inventory inventory = new Inventory(1L,1L, 100);
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        InventoryResponse response = inventoryService.purchaseProduct(new PurchaseRequest(1L, 10));
        assertEquals(90, response.getQuantity());
    }

    @Test
    void shouldSavePurchaseHistoryAndPublishEvent() {
        // Given
        Long productId = 1L;
        int quantity = 5;

        // When
        inventoryService.savePurchaseHistory(productId, quantity);

        // Then: verifica que se guarde el historial
        ArgumentCaptor<PurchaseHistoryEntity> historyCaptor = ArgumentCaptor.forClass(PurchaseHistoryEntity.class);
        verify(purchaseHistoryRepository, times(1)).save(historyCaptor.capture());
        PurchaseHistoryEntity savedEntity = historyCaptor.getValue();

        assertThat(savedEntity.getProductId()).isEqualTo(productId);
        assertThat(savedEntity.getQuantity()).isEqualTo(quantity);
        assertThat(savedEntity.getPurchaseDate()).isBeforeOrEqualTo(LocalDateTime.now());

        // Then: verifica que se publique el evento
        verify(inventoryEventProducer, times(1)).publishInventoryChange(productId, quantity);
    }

    @Test
    void testGetInventoryByProductId_NotFound() {
        when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inventoryService.getStock(1L));
    }
}
