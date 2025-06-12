package com.linktic.inventario.service;

import com.linktic.inventario.dto.InventoryRequest;
import com.linktic.inventario.dto.InventoryResponse;
import com.linktic.inventario.dto.PurchaseRequest;
import com.linktic.inventario.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryService {

    InventoryResponse getStock(Long productId);

    InventoryResponse  updateStock(Long productId, InventoryRequest request);

    InventoryResponse purchaseProduct(PurchaseRequest request);


}
