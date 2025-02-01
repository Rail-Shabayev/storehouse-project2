package com.example.rail.service;

import com.example.rail.dto.product.ProductDto;
import com.example.rail.exception.ProductNotFoundException;
import com.example.rail.mapper.ProductMapper;
import com.example.rail.model.Product;
import com.example.rail.repository.ProductRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ProductServiceImpl productService;

    Product product = Instancio.of(Product.class).create();
    ProductDto productDto = Instancio.create(ProductDto.class);
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    @DisplayName("get all product objects from database")
    void shouldReturnAllProducts() {
        List<Product> productList = List.of(product);
        List<ProductDto> productDtoList = List.of(productDto);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(productList));
        when(productMapper.productToDto(any(Product.class))).thenReturn(productDto);
        Page<ProductDto> actualProductList = productService.getAllProducts(Pageable.unpaged());

        assertThat(actualProductList.getContent().get(0).getName()).isEqualTo(productDtoList.get(0).getName());
    }

    @Test
    @DisplayName("get product object from database by it's UUID")
    void shouldReturnProductByUUID() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        when(productMapper.productToDto(any(Product.class))).thenReturn(productDto);
        ProductDto productDto1 = productService.getProduct(product.getUuid());

        assertThat(productDto1.getName()).isEqualTo(productDto.getName());
        assertThat(productDto1.getArticle()).isEqualTo(productDto.getArticle());
    }

    @Test
    @DisplayName("get product object from database and throw ProductNotFoundException")
    void shouldReturnProductNotFoundException() {
        assertThatThrownBy(() -> productService.getProduct(UUID.randomUUID()))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("save product object to the database")
    void shouldSaveProduct() {
        productRepository.save(product);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo(product.getName());
        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    @DisplayName("put product object to database given UUID and Product")
    void shouldPutProduct() {
        product.setName("something");
        productRepository.save(product);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("something");
        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    @DisplayName("delete product object from database by UUID")
    void shouldDeleteProduct() {
        productRepository.delete(product);
        verify(productRepository, times(1)).delete(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo(product.getDescription());    }
}
