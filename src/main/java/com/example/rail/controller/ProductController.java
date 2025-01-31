package com.example.rail.controller;

import com.example.rail.dto.product.CreateProductDto;
import com.example.rail.dto.product.ProductDto;
import com.example.rail.dto.product.ProductResponseDto;
import com.example.rail.dto.product.UpdateProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Tag(name = "Product", description = "Product Api")
public interface ProductController {
    @Operation(summary = "Fetch products from DB")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}")))
    })
    Page<ProductResponseDto> getAllProducts(@Parameter(required = true, description = "Pageable object") Pageable pageable);

    @Operation(summary = "Fetch product by UUID from DB")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
            @ApiResponse(
                    description = "Product not found",
                    responseCode = "404",
                    content = @Content(schema = @Schema(example = "Product not found")))
    })
    ProductResponseDto getProduct(@Parameter(required = true, description = "UUID of product") UUID uuid);

    @Operation(summary = "Save product to DB")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Product saved",
                    responseCode = "201",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(example = "d10a6c9f-d951-4a79-8518-0009af797092"))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
    })
    UUID saveProduct(@Parameter(description = "Product object", required = true) CreateProductDto product);

    @Operation(summary = "Update product in DB")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Product updated",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(example = ""))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
            @ApiResponse(
                    description = "Product not found",
                    responseCode = "404",
                    content = @Content(schema = @Schema(example = "Product not found")))
    })
    void putProduct(@Parameter(required = true, description = "Product object") UpdateProductDto productDto,
                    @Parameter(required = true, description = "UUID of product") UUID uuid);

    @Operation(summary = "Delete product from DB")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "204",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(example = ""))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
            @ApiResponse(
                    description = "Product not found",
                    responseCode = "404",
                    content = @Content(schema = @Schema(example = "Product not found"))),
    })
    void deleteProduct(@Parameter(required = true, description = "UUID of product") UUID uuid);
}
