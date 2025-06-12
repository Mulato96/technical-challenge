package com.linktic.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryChangedEvent {
    private Long productId;
    private Integer quantity;
    private String type; //"purchase", "update"
    private LocalDateTime timestamp;
}
