package com.cognizant.billing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bills")
public class BillingController {

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return List.of(
                Map.of("billId", 101, "customerId", 1, "amount", 2500.00, "status", "PAID"),
                Map.of("billId", 102, "customerId", 2, "amount", 1200.00, "status", "PENDING"),
                Map.of("billId", 103, "customerId", 1, "amount", 850.00, "status", "PAID")
        );
    }

    @GetMapping("/customer/{customerId}")
    public Map<String, Object> byCustomer(@PathVariable Integer customerId) {
        return Map.of(
                "customerId", customerId,
                "totalDue", 2050.00,
                "invoices", List.of(101, 103)
        );
    }
}
