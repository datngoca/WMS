package com.datngoc.wms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @EntityGraph(attributePaths = {"parent"})
    Optional<Category> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"parent"})
    List<Category> findAll();

    // Tìm tất con bằng path
    List<Category> findByPathStartingWith(String path);

    // Tìm các danh mục gốc
    List<Category> findByParentIsNull();

    boolean existsByParentId(Long parentId);

    boolean existsByName(String name);

    Optional<Category> findBySlug(String slug);

    @org.springframework.data.jpa.repository.Modifying(clearAutomatically = true)
    @org.springframework.data.jpa.repository.Query("UPDATE Category c SET c.isOpen = :isOpen, c.updatedAt = CURRENT_TIMESTAMP WHERE c.id = :id")
    int updateIsOpen(@org.springframework.data.repository.query.Param("id") Long id, @org.springframework.data.repository.query.Param("isOpen") Boolean isOpen);

    @org.springframework.data.jpa.repository.Modifying(clearAutomatically = true)
    @org.springframework.data.jpa.repository.Query("UPDATE Category c SET c.isOpen = (CASE WHEN c.isOpen = true THEN false ELSE true END), c.updatedAt = CURRENT_TIMESTAMP WHERE c.id = :id")
    int toggleIsOpen(@org.springframework.data.repository.query.Param("id") Long id);
}
