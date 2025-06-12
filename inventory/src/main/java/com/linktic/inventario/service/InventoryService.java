package com.linktic.inventario.service;

import com.linktic.inventario.dto.*;
import com.linktic.inventario.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryService {

    InventoryResponse getStock(Long productId);

    InventoryResponse  updateStock(Long productId, InventoryRequest request);

    CreateInventoryResponse saveStock(CreateInventoryRequest request);

    InventoryResponse purchaseProduct(PurchaseRequest request);


}
