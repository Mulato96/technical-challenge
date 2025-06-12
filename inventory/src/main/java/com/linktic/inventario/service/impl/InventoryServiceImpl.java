package com.linktic.inventario.service.impl;

import com.linktic.inventario.dto.*;
import com.linktic.inventario.entity.Inventory;
import com.linktic.inventario.entity.PurchaseHistoryEntity;
import com.linktic.inventario.exception.BusinessException;
import com.linktic.inventario.kafka.InventoryEventProducer;
import com.linktic.inventario.repository.InventoryRepository;
import com.linktic.inventario.repository.PurchaseHistoryRepository;
import com.linktic.inventario.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    private final InventoryEventProducer inventoryEventProducer;

    @Override
    public InventoryResponse getStock(Long productId) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException("Inventario no encontrado para producto: " + productId));

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantity());
    }

    @Override
    @Transactional
    public InventoryResponse  updateStock(Long productId, InventoryRequest request) {
        Inventory inventory = repository.findByProductId(productId)
                .orElse(new Inventory(null,productId, 0));

        inventory.setQuantity(request.getQuantity());
        repository.save(inventory);

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantity());
    }

    @Override
    @Transactional
    public CreateInventoryResponse saveStock(CreateInventoryRequest request) {
        if (repository.existsByProductId(request.getProductId())) {
            throw new IllegalArgumentException("Inventory for product already exists.");
        }

        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setQuantity(request.getQuantity());

        Inventory saved = repository.save(inventory);

        return new CreateInventoryResponse(saved.getId(), saved.getProductId(), saved.getQuantity());
    }

    @Override
    @Transactional
    public InventoryResponse purchaseProduct(PurchaseRequest request) {
        Inventory inventory = repository.findByProductId(request.getProductId())
                .orElseThrow(() -> new BusinessException("Inventario no disponible para producto: " + request.getProductId()));

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new BusinessException("Stock insuficiente para producto: " + request.getProductId());
        }

        inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
        repository.save(inventory);
        savePurchaseHistory(request.getProductId(), request.getQuantity());

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantity());
    }

    public void savePurchaseHistory(Long productId, int quantity) {
        PurchaseHistoryEntity history = PurchaseHistoryEntity.builder()
                .productId(productId)
                .quantity(quantity)
                .purchaseDate(LocalDateTime.now())
                .build();
        purchaseHistoryRepository.save(history);
        inventoryEventProducer.publishInventoryChange(productId, quantity);
    }

}
