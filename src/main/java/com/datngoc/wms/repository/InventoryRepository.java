package com.datngoc.wms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datngoc.wms.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    @Query(value = "SELECT i FROM Inventory i LEFT JOIN FETCH i.product",
           countQuery = "SELECT count(i) FROM Inventory i")
    Page<Inventory> findAllWithProduct(Pageable pageable);
}
