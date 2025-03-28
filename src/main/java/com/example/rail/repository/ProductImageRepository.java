package com.example.rail.repository;

import com.example.rail.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    @Query(value = "SELECT pi.imageId FROM ProductImage pi WHERE pi.product.id = :productId")
    List<String> findAllByProductId(UUID productId);
}
