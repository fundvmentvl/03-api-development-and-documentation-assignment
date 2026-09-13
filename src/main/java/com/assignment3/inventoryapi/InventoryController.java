package com.assignment3.inventoryapi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/items")
public class InventoryController {
    private final List<InventoryItem> inventory = new ArrayList<>();
    public InventoryController() {
        inventory.add(new InventoryItem(1001, "Laptop", 899.99));
        inventory.add(new InventoryItem(1002, "Monitor", 249.99));
        inventory.add(new InventoryItem(1003, "Mouse", 29.99));
    }
    @GetMapping
    public List<InventoryItem> getAllItems() {
        return inventory;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable int id) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "ITEM_NOT_FOUND",
                        "No inventory item exists with ID " + id
                ));
    }
    @PostMapping
    public ResponseEntity<?> addItem(
            @RequestBody InventoryItem newItem
    ) {
        if (newItem.getId() <= 0
                || newItem.getName() == null
                || newItem.getName().isBlank()
                || newItem.getPrice() < 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiError(
                            "INVALID_ITEM",
                            "Item ID must be positive, name is required, and price cannot be negative"
                    ));
        }
        for (InventoryItem item : inventory) {
            if (item.getId() == newItem.getId()) {
                return ResponseEntity.badRequest()
                        .body(new ApiError(
                                "DUPLICATE_ID",
                                "An inventory item with ID "
                                        + newItem.getId()
                                        + " already exists"
                        ));
            }
        }
        inventory.add(newItem);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newItem);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable int id,
            @RequestBody InventoryItem updatedItem
    ) {
        if (updatedItem.getName() == null
                || updatedItem.getName().isBlank()
                || updatedItem.getPrice() < 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiError(
                            "INVALID_ITEM",
                            "Name is required and price cannot be negative"
                    ));
        }
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                item.setName(updatedItem.getName());
                item.setPrice(updatedItem.getPrice());
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "ITEM_NOT_FOUND",
                        "No inventory item exists with ID " + id
                ));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                inventory.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "ITEM_NOT_FOUND",
                        "No inventory item exists with ID " + id
                ));
    }
}