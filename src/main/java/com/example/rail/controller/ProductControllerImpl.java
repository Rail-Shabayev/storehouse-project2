package com.example.rail.controller;

import com.example.rail.dto.product.CreateProductDto;
import com.example.rail.dto.product.ProductDto;
import com.example.rail.dto.product.ProductResponseDto;
import com.example.rail.dto.product.UpdateProductDto;
import com.example.rail.mapper.ProductMapper;
import com.example.rail.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductServiceImpl productServiceImpl;
    private final ProductMapper productMapper;

    @GetMapping
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return new PageImpl<>(productServiceImpl.getAllProducts(pageable)
                .stream()
                .map(productMapper::dtoToResponse)
                .toList());
    }

    @GetMapping("/{uuid}")
    public ProductResponseDto getProduct(@PathVariable("uuid") UUID uuid) {
        return productMapper.dtoToResponse(productServiceImpl.getProduct(uuid));
    }

    @PostMapping
    @ResponseStatus(CREATED) //201
    public UUID saveProduct(@RequestBody @Valid CreateProductDto product) {
        ProductDto productDto = productMapper.createProductDtoToProductDto(product);
        return productServiceImpl.saveProduct(productDto);
    }

    @PutMapping("/{uuid}")
    public void putProduct(@RequestBody @Valid UpdateProductDto productDto, @PathVariable("uuid") UUID uuid) {
        ProductDto updatedProduct = productMapper.updateProductDtoToProductDto(productDto);
        productServiceImpl.putProduct(uuid, updatedProduct);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(NO_CONTENT) //204
    public void deleteProduct(@PathVariable("uuid") UUID uuid) {
        productServiceImpl.deleteProduct(uuid);
    }
}
