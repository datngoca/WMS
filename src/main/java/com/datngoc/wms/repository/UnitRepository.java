package com.datngoc.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    boolean existsByName(String name);
}
