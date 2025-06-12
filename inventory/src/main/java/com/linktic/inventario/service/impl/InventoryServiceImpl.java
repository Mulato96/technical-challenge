package com.linktic.inventario.service.impl;

import com.linktic.inventario.dto.InventoryRequest;
import com.linktic.inventario.dto.InventoryResponse;
import com.linktic.inventario.dto.PurchaseRequest;
import com.linktic.inventario.entity.Inventory;
import com.linktic.inventario.exception.BusinessException;
import com.linktic.inventario.repository.InventoryRepository;
import com.linktic.inventario.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

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
                .orElse(new Inventory(productId, 0));

        inventory.setQuantity(request.getQuantity());
        repository.save(inventory);

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantity());
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

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantity());
    }

}
