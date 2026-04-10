package com.datngoc.wms.entity;

import com.datngoc.wms.entity.json.ProductDetailedSpec;
import com.datngoc.wms.entity.json.ProductSpec;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Nhãn dán: Class này là một bảng trong DB
@Table(name = "products") // Nhãn dán: Tên bảng được DB sẽ là products
@Getter
@Setter
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String sku;

    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductUnit> productUnits;

    // ----- CÁC TRƯỜNG LƯU DẠNG JSON -----

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<ProductSpec> specs;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detailed_specs", columnDefinition = "json")
    private List<ProductDetailedSpec> detailedSpecs;

    // ----- QUAN HỆ VỚI BẢNG OPTION -----
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    public Product() {
    }
}
