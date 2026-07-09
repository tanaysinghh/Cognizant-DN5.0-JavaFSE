package com.cognizant.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

// name matches spring.application.name of the target service. Eureka + LoadBalancer resolves it.
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{id}")
    Map<String, Object> getProduct(@PathVariable("id") Long id);
}
