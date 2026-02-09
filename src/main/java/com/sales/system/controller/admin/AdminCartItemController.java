package com.sales.system.controller.admin;

import com.sales.system.dto.admin.cart.AdminCartItemUpdateDTO;
import com.sales.system.entity.CartItem;
import com.sales.system.service.cart.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cart-items")
public class AdminCartItemController {

    private final CartItemService cartItemService;

    public AdminCartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> listAll() {
        return ResponseEntity.ok(cartItemService.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> update(
            @PathVariable Long id,
            @RequestBody AdminCartItemUpdateDTO dto
    ) {
        return ResponseEntity.ok(
                cartItemService.updateQuantity(id, dto.getQuantity())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
