package com.example.fashionshop;

import com.example.fashionshop.model.Order;
import com.example.fashionshop.model.Product;
import com.example.fashionshop.model.User;
import com.example.fashionshop.repository.ProductRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;




public class Main {


    public static void main(String[] args) {

        Order order = new Order();
        order.setUser(new User());
        order.setProduct(new Product());

        System.out.println(new Gson().toJson(order));


    }
}