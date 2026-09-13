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
    public ResponseEntity<InventoryItem> getItemById(@PathVariable int id) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<InventoryItem> addItem(
            @RequestBody InventoryItem newItem
    ) {
        inventory.add(newItem);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(
            @PathVariable int id,
            @RequestBody InventoryItem updatedItem

    ) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                item.setName(updatedItem.getName());
                item.setPrice(updatedItem.getPrice());
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                inventory.remove(item);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}