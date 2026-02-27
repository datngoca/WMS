package com.datngoc.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

}
