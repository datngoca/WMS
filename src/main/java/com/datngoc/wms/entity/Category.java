package com.datngoc.wms.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.datngoc.wms.utils.StringUtils;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity {
    private String name;

    private String slug;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    private String path; // Ví dụ: "1/5/12/"

    private Integer depth; // Độ sâu

    @ManyToMany(mappedBy = "categories")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Product> products = new HashSet<>();

    // Helper method để cập nhật path trước khi save
    @PrePersist
    @PreUpdate
    public void preSave() {
        // Cập nhật slug
        if (this.name != null) {
            this.slug = StringUtils.toSlug(this.name);
        }

        // Cập nhật path và depth
        if (parent == null) {
            this.path = "";
            this.depth = 0;
        } else {
            this.path = parent.getPath() + parent.getId() + "/";
            this.depth = parent.getDepth() + 1;
        }
    }
}
