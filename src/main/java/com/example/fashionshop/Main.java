package com.example.fashionshop;

import com.example.fashionshop.model.Order;
import com.example.fashionshop.model.Product;
import com.example.fashionshop.model.User;
import com.example.fashionshop.repository.ProductRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {
    @Autowired
    private static ProductRepository productRepository;
    public static void main(String[] args) {
        Order order = new Order();
        order.setUser(new User());
        order.setProduct(new Product());

        System.out.println(new Gson().toJson(order));

    }
}