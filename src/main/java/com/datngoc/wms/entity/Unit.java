package com.datngoc.wms.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Unit extends BaseEntity {
    private String name;
}
