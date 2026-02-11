package com.sales.system.controller.user;

import com.sales.system.dto.cart.AddItemRequest;
import com.sales.system.dto.cart.CartResponseDTO;
import com.sales.system.dto.user.UpdateItemRequest;
import com.sales.system.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(
        name = "Cart",
        description = "Operações do carrinho do usuário autenticado"
)
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "Visualizar carrinho",
            description = "Retorna o carrinho do usuário autenticado"
    )
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(Authentication authentication) {
        return ResponseEntity.ok(
                cartService.getCartResponse(authentication.getName())
        );
    }

    @Operation(
            summary = "Adicionar item ao carrinho",
            description = "Adiciona um produto ao carrinho do usuário autenticado"
    )
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

    @Operation(
            summary = "Atualizar item do carrinho",
            description = "Atualiza a quantidade de um produto no carrinho"
    )
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

    @Operation(
            summary = "Remover item do carrinho",
            description = "Remove um produto do carrinho"
    )
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            Authentication authentication,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                cartService.removeItem(authentication.getName(), productId)
        );
    }

    @Operation(
            summary = "Total do carrinho",
            description = "Retorna o valor total do carrinho"
    )
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(Authentication authentication) {
        return ResponseEntity.ok(
                cartService.getTotal(authentication.getName())
        );
    }

    @Operation(
            summary = "Limpar carrinho",
            description = "Remove todos os itens do carrinho"
    )
    @DeleteMapping
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        cartService.clearCart(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
