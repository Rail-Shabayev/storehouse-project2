package com.example.rail.dto.order;

import com.example.rail.dto.product.ProductInOrderDto;
import lombok.Data;

import java.util.List;

@Data
public class UpdateOrderDto {
    private List<ProductInOrderDto> products;
}