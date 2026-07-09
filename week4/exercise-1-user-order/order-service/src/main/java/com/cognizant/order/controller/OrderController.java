package com.cognizant.order.controller;

import com.cognizant.order.client.UserClient;
import com.cognizant.order.dto.OrderResponse;
import com.cognizant.order.dto.UserDto;
import com.cognizant.order.entity.Order;
import com.cognizant.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserClient userClient;

    @GetMapping
    public List<Order> getAll() {
        return repository.findAll();
    }

    // Fetches an order and, via Feign, the user who placed it.
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return repository.findById(id).map(order -> {
            UserDto user = userClient.getUser(order.getUserId());
            return ResponseEntity.ok(new OrderResponse(order, user));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        // Validate user exists before creating order
        userClient.getUser(order.getUserId());
        return repository.save(order);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getByUser(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }
}
