package com.datngoc.wms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Tìm tất con bằng path
    List<Category> findByPathStartingWith(String path);

    // Tìm các danh mục gốc
    List<Category> findByParentIsNull();

    boolean existsByParentId(Long parentId);

    boolean existsByName(String name);

    Optional<Category> findBySlug(String slug);
}
