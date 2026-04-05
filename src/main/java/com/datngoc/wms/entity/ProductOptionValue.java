package com.datngoc.wms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_option_values")
@Getter
@Setter
public class ProductOptionValue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption option;

    private String label; // VD: "Black", "256GB"

    private String value; // VD: "#000000", "256GB"
}
