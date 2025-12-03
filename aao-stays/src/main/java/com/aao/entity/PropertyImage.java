package com.aao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PROPERTY_IMAGE", indexes = {
        @Index(name = "idx_property_id", columnList = "property_id"),
        @Index(name = "idx_display_order", columnList = "display_order"),
        @Index(name = "idx_is_primary", columnList = "is_primary")
})
public class PropertyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({"images", "host", "hibernateLazyInitializer"})
    private Property property;




    @Column(name = "image_url", nullable = false, length = 512)
    private String imageUrl;

    @Column(name = "image_caption")
    private String imageCaption;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
