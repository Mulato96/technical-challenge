package com.linktic.inventario.controller;

import com.linktic.inventario.api.JsonApiResponse;
import com.linktic.inventario.api.JsonApiUtils;
import com.linktic.inventario.dto.InventoryRequest;
import com.linktic.inventario.dto.InventoryResponse;
import com.linktic.inventario.dto.PurchaseRequest;
import com.linktic.inventario.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<JsonApiResponse> getStock(@PathVariable Long productId) {
        InventoryResponse response = inventoryService.getStock(productId);
        return ResponseEntity.ok(JsonApiUtils.buildResponse("inventory", String.valueOf(response.getProductId()), response));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<JsonApiResponse> updateInventory(
            @PathVariable Long productId,
            @RequestBody @Valid InventoryRequest request
    ) {
        InventoryResponse response = inventoryService.updateStock(productId, request);
        return ResponseEntity.ok(JsonApiUtils.buildResponse("inventory", String.valueOf(response.getProductId()), response));
    }

    @PostMapping("/purchase")
    public ResponseEntity<JsonApiResponse> purchaseProduct(@RequestBody @Valid PurchaseRequest request) {
        InventoryResponse response = inventoryService.purchaseProduct(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JsonApiUtils.buildResponse("inventory", String.valueOf(response.getProductId()), response));
    }
}
