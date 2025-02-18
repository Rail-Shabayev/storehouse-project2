package com.example.rail.service;

import com.example.rail.dto.search.AbstractCriteria;
import com.example.rail.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Page<ProductDto> getAllProducts(Pageable pageable);

    ProductDto getProduct(UUID uuid);

    UUID saveProduct(ProductDto productDto);

    void putProduct(UUID uuid, ProductDto productDto);

    void deleteProduct(UUID uuid);

    Page<ProductDto> searchProduct(Pageable pageable, List<AbstractCriteria<?>> abstractCriteria);
}

