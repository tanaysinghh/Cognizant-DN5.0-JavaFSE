package com.cognizant.payment.controller;

import com.cognizant.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public String pay(@RequestParam(defaultValue = "0") double amount) {
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);
        return paymentService.makePayment(paymentId, amount);
    }

    // Quick GET endpoint for browser testing
    @GetMapping("/test")
    public String test() {
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);
        return paymentService.makePayment(paymentId, 1000.00);
    }
}
