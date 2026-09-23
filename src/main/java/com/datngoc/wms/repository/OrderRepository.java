package com.datngoc.wms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);
}
