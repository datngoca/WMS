package com.datngoc.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    
}

