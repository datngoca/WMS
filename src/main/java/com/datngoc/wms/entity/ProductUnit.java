package com.datngoc.wms.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_units")
public class ProductUnit extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private Double exchangeValue;

    private BigDecimal price;

    private Boolean isBaseUnit;

}
