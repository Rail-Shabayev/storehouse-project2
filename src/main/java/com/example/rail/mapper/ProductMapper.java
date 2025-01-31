package com.example.rail.mapper;

import com.example.rail.dto.product.CreateProductDto;
import com.example.rail.dto.product.ProductDto;
import com.example.rail.dto.product.ProductResponseDto;
import com.example.rail.dto.product.UpdateProductDto;
import com.example.rail.model.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface ProductMapper {

    ProductDto productToDto(Product product);

    Product dtoToProduct(ProductDto productDto);

    ProductResponseDto dtoToResponse(ProductDto productDto);

    ProductDto createProductDtoToProductDto(CreateProductDto createProductDto);

    ProductDto updateProductDtoToProductDto(UpdateProductDto updateProductDto);
}
