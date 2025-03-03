package com.example.rail.repository;

import com.example.rail.dto.order.OrderItemDto;
import com.example.rail.model.OrderItem;
import com.example.rail.model.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    @Query("SELECT new com.example.rail.dto.order.OrderItemDto(prod.id AS product_id, prod.name, oi.quantity, oi.price) " +
            "FROM order_item AS oi " +
            "INNER JOIN product AS prod " +
            "WHERE oi.order.id = :orderId")
    List<OrderItemDto> findProductsByOrderId(UUID orderId);
}

