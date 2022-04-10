package com.example.fashionshop.controller;

import com.example.fashionshop.model.Order;
import com.example.fashionshop.model.commons.enums.OrderStatus;
import com.example.fashionshop.model.dto.requestDto.OrderUpdateReqDto;
import com.example.fashionshop.model.dto.requestDto.ResponseDto;
import com.example.fashionshop.service.OrderService;
import com.example.fashionshop.validation.OrderValidator;
import com.example.fashionshop.validation.UserValidator;
import com.example.fashionshop.validation.dto.OrderDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("get-all")
    ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/user-order")
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader("user_id") String userId) {

        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }
        return ResponseEntity.ok(orderService.getAllById(userId));

    }

    @GetMapping("/order-status")
    ResponseEntity<List<Order>> getOrderByStatus(@RequestHeader("user_id") String userId,
                                                 @RequestHeader("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }

        return ResponseEntity.ok(orderService.getOrderByStatus(userId, orderStatus));
    }

    @PostMapping
    ResponseEntity<ResponseDto> create(@RequestBody Order order,
                                       @RequestHeader String userId) {
        if (!OrderValidator.validateOrder(order, userId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order Structure for accepting Order"
            );
        }
        Order created = orderService.create(order);
        ResponseDto responseDto = new ResponseDto("Order created.");
        responseDto.addInfo("OrderId", String.valueOf(created.getId()));
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/change-status/{order_id}/{status}")
    ResponseEntity<ResponseDto> changeStatus(@RequestHeader("user_id") String userId,
                                             @PathVariable("order_id") Long orderId,
                                             @PathVariable("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }
        orderService.changeStatus(orderId, orderStatus);
        ResponseDto responseDto = new ResponseDto("Status is change.");
        responseDto.addInfo("OrderStatus", String.valueOf(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{order_id}")
    ResponseEntity<ResponseDto> delete(@PathVariable("order_id") Long id,
                                       @RequestHeader String userId) {
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is unauthorized, please sign in first:"
            );
        }
        orderService.delete(id);
        ResponseDto responseDto = new ResponseDto("Order GBRSH.");
        responseDto.addInfo("OrderId", String.valueOf(id));
        return ResponseEntity.ok(responseDto);
    }
}