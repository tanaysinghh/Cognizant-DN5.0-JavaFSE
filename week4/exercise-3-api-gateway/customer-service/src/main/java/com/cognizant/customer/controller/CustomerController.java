package com.cognizant.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return List.of(
                Map.of("id", 1, "name", "Tanay", "tier", "PREMIUM"),
                Map.of("id", 2, "name", "Aum", "tier", "STANDARD"),
                Map.of("id", 3, "name", "Palak", "tier", "PREMIUM")
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable Integer id) {
        Map<String, Object> customer = new HashMap<>();
        customer.put("id", id);
        customer.put("name", "Customer-" + id);
        customer.put("tier", id % 2 == 0 ? "STANDARD" : "PREMIUM");
        return customer;
    }
}
