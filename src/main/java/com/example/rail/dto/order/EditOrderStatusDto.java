package com.example.rail.dto.order;

import com.example.rail.model.OrderStatus;
import lombok.Data;

@Data
public class EditOrderStatusDto {
    private OrderStatus status;
}
