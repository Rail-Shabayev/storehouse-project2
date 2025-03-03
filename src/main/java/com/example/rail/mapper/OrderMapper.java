package com.example.rail.mapper;

import com.example.rail.dto.order.AddOrderDto;
import com.example.rail.dto.order.CreateOrderDto;
import com.example.rail.dto.order.EditOrderDto;
import com.example.rail.dto.order.OrderInfoDto;
import com.example.rail.dto.order.OrderResponseDto;
import com.example.rail.dto.order.UpdateOrderDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface OrderMapper {
    EditOrderDto toEditOrderDto(UpdateOrderDto updateOrderDto);
    OrderResponseDto toOrderResponseDto(OrderInfoDto orderInfoDto);
    AddOrderDto toAddOrderDto(CreateOrderDto createOrderDto);
}
