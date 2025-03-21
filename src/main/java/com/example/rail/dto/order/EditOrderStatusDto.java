package com.example.rail.dto.order;

import com.example.rail.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditOrderStatusDto {
    private OrderStatus status;
}
