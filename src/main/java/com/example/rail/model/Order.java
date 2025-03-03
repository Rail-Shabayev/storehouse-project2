package com.example.rail.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "[order]")
@Builder
@AllArgsConstructor
public class Order {
    @Id
    @UuidGenerator
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "orderStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "delivery_address", nullable = false)
    @NotBlank(message = "Order delivery address can't be null or empty")
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> orderItems;
}
