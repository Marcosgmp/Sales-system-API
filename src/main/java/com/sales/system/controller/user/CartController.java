package com.sales.system.controller.user;

import com.sales.system.dto.cart.AddItemRequest;
import com.sales.system.dto.cart.CartResponseDTO;
import com.sales.system.dto.user.UpdateItemRequest;
import com.sales.system.service.cart.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(Authentication authentication) {
        return ResponseEntity.ok(
                cartService.getCartResponse(authentication.getName())
        );
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(
            Authentication authentication,
            @Valid @RequestBody AddItemRequest request
    ) {
        return ResponseEntity.ok(
                cartService.addItem(
                        authentication.getName(),
                        request.getProductId(),
                        request.getQuantity()
                )
        );
    }

    @PutMapping("/items")
    public ResponseEntity<CartResponseDTO> updateItem(
            Authentication authentication,
            @Valid @RequestBody UpdateItemRequest request
    ) {
        return ResponseEntity.ok(
                cartService.updateQuantity(
                        authentication.getName(),
                        request.getProductId(),
                        request.getQuantity()
                )
        );
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            Authentication authentication,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                cartService.removeItem(authentication.getName(), productId)
        );
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(Authentication authentication) {
        return ResponseEntity.ok(
                cartService.getTotal(authentication.getName())
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        cartService.clearCart(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}