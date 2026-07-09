package com.cognizant.product.controller;

import com.cognizant.product.entity.Product;
import com.cognizant.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Value("${product.welcome-message:default message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage;
    }

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        return repository.findById(id).map(p -> {
            p.setStock(stock);
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }
}
