package com.example.rail.dto.order;

import com.example.rail.dto.product.ProductInOrderDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EditOrderDto {
    private List<ProductInOrderDto> products;
}
