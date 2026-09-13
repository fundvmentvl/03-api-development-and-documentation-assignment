package com.assignment3.inventoryapi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/items")
public class InventoryController {
    @GetMapping
    public String getItems() {
        return "Inventory API is running";
    }
}