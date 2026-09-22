package com.datngoc.wms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.datngoc.wms.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    List<Product> findByCategories_Id(Long categoryId);

    @Query("SELECT p.sku FROM Product p WHERE p.sku LIKE CONCAT(:prefix, '%')")
    List<String> findSkusByPrefix(@Param("prefix") String prefix);
}
