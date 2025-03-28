package com.example.rail.controller;

import com.example.rail.dto.product.CreateProductDto;
import com.example.rail.dto.product.ProductDto;
import com.example.rail.dto.product.ProductResponseDto;
import com.example.rail.dto.product.UpdateProductDto;
import com.example.rail.dto.search.AbstractCriteria;
import com.example.rail.mapper.ProductMapper;
import com.example.rail.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductServiceImpl productServiceImpl;
    private final ProductMapper productMapper;

    @PostMapping(value = "/{id}/upload", consumes = MULTIPART_FORM_DATA)
    public UUID uploadFile(@PathVariable UUID id, @RequestParam MultipartFile multipartFile) throws IOException {
        return productServiceImpl.uploadProductImage(id, multipartFile);
    }

    @GetMapping(value ="/{id}/download", produces ="application/zip")
    public void downloadProductImagesZip(@PathVariable UUID id) {
        productServiceImpl.downloadProductImagesZip(id);
    }

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

    @PostMapping("/{search}")
    public Page<ProductResponseDto> searchProduct(Pageable pageable, @RequestBody List<AbstractCriteria<?>> abstractCriteria) {
        return new PageImpl<>(productServiceImpl.searchProduct(pageable, abstractCriteria)
                .stream()
                .map(productMapper::dtoToResponse)
                .toList());
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
