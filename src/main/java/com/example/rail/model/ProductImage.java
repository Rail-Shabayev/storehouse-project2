package com.example.rail.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
    @Id
    @UuidGenerator
    @Column(name = "image_id", nullable = false)
    private UUID imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
