package com.example.rail.repository;

import com.example.rail.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>,
        PagingAndSortingRepository<Order, UUID> {
    @Query("SELECT o FROM com.example.rail.model.Order o " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH o.customer " +
            "LEFT JOIN FETCH oi.product p " +
            "LEFT JOIN FETCH p.category " +
            "WHERE o.id = :orderId")
    Optional<Order> findByIdFetchOrderItems(UUID orderId);
}
