package com.example.rail.service;

import com.example.rail.dto.product.ProductDto;
import com.example.rail.dto.product.ProductResponseDto;
import com.example.rail.dto.search.AbstractCriteria;
import com.example.rail.exception.ArticleAlreadyExistsException;
import com.example.rail.exception.ProductNotFoundException;
import com.example.rail.mapper.ProductMapper;
import com.example.rail.model.Product;
import com.example.rail.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return new PageImpl<>(productRepository.findAll(pageable)
                .stream()
                .map(mapper::productToDto)
                .toList());
    }

    public ProductDto getProduct(UUID uuid) {
        return mapper.productToDto(productRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product with that uuid is absent in DB")));
    }

    public UUID saveProduct(ProductDto productDto) {
        if (productRepository.existsByArticle(productDto.getArticle()))
            throw new ArticleAlreadyExistsException("Product with that article already exists in DB");
        Product product = mapper.dtoToProduct(productDto);
        productRepository.save(product);
        return product.getUuid();
    }

    public void putProduct(UUID uuid, ProductDto productDto) {
        Product product = productRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product with that uuid is absent in DB"));
        Long dtoArticle = productDto.getArticle();
        if (!(product.getArticle().equals(dtoArticle)) && productRepository.existsByArticle(dtoArticle))
            throw new ArticleAlreadyExistsException("Product with that article already exists in DB");
        Product productToSave = mapper.dtoToProduct(productDto);
        productToSave.setUuid(uuid);
        productRepository.save(productToSave);
    }

    public void deleteProduct(UUID uuid) {
        productRepository.deleteById(uuid);
    }

    public Page<ProductResponseDto> searchProduct(Pageable pageable, List<AbstractCriteria<?>> abstractCriteria) {
        if (abstractCriteria.isEmpty()) {
            return null;
        }
        Specification<Product> result = null;
        for (AbstractCriteria<?> criteria : abstractCriteria) {
            result = Specification.where(result).and(criteria.getStrategy());
        }
        Page<ProductDto> productDtos = productRepository.findAll(result, pageable).map(mapper::productToDto);
        return productDtos.map(mapper::dtoToResponse);
    }
}
