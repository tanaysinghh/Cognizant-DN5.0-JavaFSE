package com.cognizant.inventory.controller;

import com.cognizant.inventory.client.ProductClient;
import com.cognizant.inventory.entity.Inventory;
import com.cognizant.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository repository;

    @Autowired
    private ProductClient productClient;

    @Value("${inventory.welcome-message:default}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage;
    }

    @GetMapping
    public List<Inventory> getAll() {
        return repository.findAll();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getStockForProduct(@PathVariable Long productId) {
        return repository.findByProductId(productId).map(inv -> {
            Map<String, Object> product = productClient.getProduct(productId);
            Map<String, Object> response = new HashMap<>();
            response.put("inventory", inv);
            response.put("product", product);
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inventory create(@RequestBody Inventory inventory) {
        return repository.save(inventory);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Inventory> updateStock(@PathVariable Long id, @RequestParam Integer stockLevel) {
        return repository.findById(id).map(inv -> {
            inv.setStockLevel(stockLevel);
            return ResponseEntity.ok(repository.save(inv));
        }).orElse(ResponseEntity.notFound().build());
    }
}
