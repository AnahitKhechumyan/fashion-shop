package com.example.fashionshop.service;

import com.example.fashionshop.model.Order;
import com.example.fashionshop.model.commons.enums.OrderStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface OrderService {
    Order create(Order order);

    List<Order> getAll();

    List<Order> getOrderByStatus(String userId, OrderStatus orderStatus);

    Order getOrdersByUserId(long orderId);

    List<Order> getAllById(String id);

    @Transactional
    void changeStatus(Long orderId, OrderStatus orderStatus);
    void delete(Long id);

}