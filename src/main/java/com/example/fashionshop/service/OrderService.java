package com.example.fashionshop.service;


import com.example.fashionshop.model.Order;
import com.example.fashionshop.model.commons.enums.OrderStatus;
import com.example.fashionshop.model.dto.requestDto.OrderUpdateReqDto;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    List<Order> getOrderByStatus(String userId, OrderStatus orderStatus);

    void delete(Long id);

    List<Order> getAllById(String id);

    Order create(Order order);


    void changeStatus(Long orderId, OrderStatus orderStatus);

    Order update(OrderUpdateReqDto reqDto, String orderId);
}