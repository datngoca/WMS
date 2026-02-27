package com.datngoc.wms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity // Nhãn dán: Class này là một bảng trong DB
@Table(name = "products") // Nhãn dán: Tên bảng duows DB sẽ là products
@Getter
@Setter
public class Product extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String sku;

    private String name;

    private String category;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    public Product() {
    }
}
