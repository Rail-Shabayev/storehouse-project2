package com.example.rail.controller;

import com.example.rail.dto.product.CreateProductDto;
import com.example.rail.dto.product.ProductResponseDto;
import com.example.rail.dto.product.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductController {
    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getProduct(UUID uuid);

    UUID saveProduct(CreateProductDto product);

    void putProduct(UpdateProductDto productDto, UUID uuid);

    void deleteProduct(UUID uuid);
}
