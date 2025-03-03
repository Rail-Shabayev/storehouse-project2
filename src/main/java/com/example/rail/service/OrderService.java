package com.example.rail.service;

import com.example.rail.dto.order.AddOrderDto;
import com.example.rail.dto.order.EditOrderDto;
import com.example.rail.dto.order.EditOrderStatusDto;
import com.example.rail.dto.order.OrderInfoDto;
import com.example.rail.dto.order.OrderItemDto;
import com.example.rail.dto.product.ProductInOrderDto;
import com.example.rail.exception.CustomerNotFoundException;
import com.example.rail.exception.CustomerOrderNotMatchException;
import com.example.rail.exception.OrderHasNotCreatedStatusException;
import com.example.rail.exception.OrderNotFoundException;
import com.example.rail.exception.ProductNotAvailableException;
import com.example.rail.exception.ProductNotEnoughException;
import com.example.rail.exception.ProductNotFoundException;
import com.example.rail.model.Customer;
import com.example.rail.model.Order;
import com.example.rail.model.OrderItem;
import com.example.rail.model.OrderItemId;
import com.example.rail.model.OrderStatus;
import com.example.rail.model.Product;
import com.example.rail.repository.CustomerRepository;
import com.example.rail.repository.OrderItemRepository;
import com.example.rail.repository.OrderRepository;
import com.example.rail.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderInfoDto findOrder(Long customerId, UUID id) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (!customerId.equals(order.getCustomer().getId())) {
            throw new CustomerOrderNotMatchException(order.getCustomer().getId());
        }
        List<OrderItemDto> products = orderItemRepository.findProductsByOrderId(id);

        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItemDto product : products) {
            BigDecimal oldTotalPrice = totalPrice;
            totalPrice = oldTotalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }

        return OrderInfoDto.builder()
                .orderId(id)
                .products(products)
                .totalPrice(totalPrice)
                .build();
    }

    @Transactional
    public UUID addOrder(Long customerId, AddOrderDto createOrderDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .deliveryAddress(createOrderDto.getDeliveryAddress())
                .customer(customer)
                .build();

        List<OrderItem> addedOrderItems = checkOrderItemList(createOrderDto.getProducts(), order);
        order.setOrderItems(addedOrderItems);

        return orderRepository.save(order).getUuid();
    }

    @Transactional
    public UUID editOrder(Long customerId, UUID id, EditOrderDto editOrderDto) {
        Order orderDb = orderRepository.findByIdFetchOrderItems(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (!customerId.equals(orderDb.getCustomer().getId())) {
            throw new CustomerOrderNotMatchException(orderDb.getCustomer().getId());
        }

        if (!orderDb.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new OrderHasNotCreatedStatusException(orderDb.getUuid());
        }

        List<OrderItem> updatedOrderItems = checkOrderItemList(editOrderDto.getProducts(), orderDb);

        Map<UUID, OrderItem> existsOrderItems = orderDb.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItem -> OrderItem.getProduct().getUuid(), Function.identity()));

        for (OrderItem updatedOrderItem : updatedOrderItems) {
            OrderItem existsOrderItem = existsOrderItems.get(updatedOrderItem.getProduct().getUuid());
            if (existsOrderItem != null) {
                existsOrderItem.setQuantity(
                        updatedOrderItem.getQuantity() + (existsOrderItem.getQuantity()));
                existsOrderItem.setPrice(updatedOrderItem.getPrice());
            } else {
                orderDb.getOrderItems().add(updatedOrderItem);
            }
        }
        return orderRepository.save(orderDb).getUuid();
    }

    @Transactional
    public void deleteOrder(Long customerId, UUID id) {
        Order order = orderRepository.findByIdFetchOrderItems(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (!customerId.equals(order.getCustomer().getId())) {
            throw new CustomerOrderNotMatchException(order.getCustomer().getId());
        }
        if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
            order.setOrderStatus(OrderStatus.CANCELLED);
        } else {
            throw new OrderHasNotCreatedStatusException(order.getUuid());
        }

        for (OrderItem OrderItem : order.getOrderItems()) {
            Product updatedProduct = OrderItem.getProduct();
            updatedProduct.setQuantity(updatedProduct.getQuantity() + (OrderItem.getQuantity()));
            productRepository.save(updatedProduct);
        }
    }

    @Transactional
    public void editOrderStatus(UUID id, EditOrderStatusDto editOrderStatusDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setOrderStatus(editOrderStatusDto.getStatus());
        orderRepository.save(order);
    }

    @Transactional
    public void confirmOrder(Long customerId, UUID id) {
        //TODO:
    }

    private List<OrderItem> checkOrderItemList(List<ProductInOrderDto> addedProductList, Order order) {
        Map<UUID, OrderItem> OrderItemsMap = new HashMap<>();

        final List<UUID> productInOrderIds = addedProductList.stream()
                .map(ProductInOrderDto::getUuid)
                .toList();

        final Map<UUID, Product> productsDbMap = productRepository.findAllById(productInOrderIds).stream()
                .collect(Collectors.toMap(Product::getUuid, Function.identity()));

        for (ProductInOrderDto productInOrder : addedProductList) {
            Product productDb = productsDbMap.get(productInOrder.getUuid());

            if (productDb == null) {
                throw new ProductNotFoundException(productInOrder.getUuid().toString());
            }
            if (!productDb.getIsAvailable()) {
                throw new ProductNotAvailableException(productInOrder.getUuid());
            } else if (productDb.getQuantity() < productInOrder.getQuantity()) {
                throw new ProductNotEnoughException(productInOrder.getUuid(), productInOrder.getQuantity(),
                        productDb.getQuantity());
            } else {
                int quantity = productInOrder.getQuantity();
                OrderItem orderItemExists = OrderItemsMap.get(productInOrder.getUuid());
                if (orderItemExists != null) {
                    orderItemExists.setQuantity(orderItemExists.getQuantity() + quantity);
                } else {
                    OrderItem orderItem = OrderItem.builder()
                            .id(new OrderItemId(order.getUuid(), productInOrder.getUuid()))
                            .product(productDb)
                            .order(order)
                            .price(productDb.getPrice())
                            .quantity(quantity)
                            .build();
                    OrderItemsMap.put(productInOrder.getUuid(), orderItem);
                }

                int newQuantityProductDb = productDb.getQuantity() - productInOrder.getQuantity();
                productDb.setQuantity(newQuantityProductDb);
            }
        }
        return OrderItemsMap.values()
                .stream()
                .toList();
    }
}
