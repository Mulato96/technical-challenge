package com.linktic.inventario.repository;

import com.linktic.inventario.entity.PurchaseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistoryEntity, Long> {
}

