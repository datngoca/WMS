package com.datngoc.wms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="warehouses")
@Getter
@Setter
public class Warehouse extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name ="address")
    private String address;

    @Column(name="capacity")
    private Integer capacity;
}
