package com.sales.system.controller.admin;

import com.sales.system.dto.admin.cart.AdminCartItemUpdateDTO;
import com.sales.system.entity.CartItem;
import com.sales.system.service.cart.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Admin - Cart Items",
        description = "Operações administrativas sobre itens do carrinho"
)
@RestController
@RequestMapping("/api/admin/cart-items")
public class AdminCartItemController {

    private final CartItemService cartItemService;

    public AdminCartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @Operation(
            summary = "Listar todos os itens de carrinho",
            description = "Retorna todos os itens de carrinho cadastrados no sistema (ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping
    public ResponseEntity<List<CartItem>> listAll() {
        return ResponseEntity.ok(cartItemService.listAll());
    }

    @Operation(
            summary = "Atualizar quantidade do item do carrinho",
            description = "Atualiza a quantidade de um item específico do carrinho (ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> update(
            @PathVariable Long id,
            @RequestBody AdminCartItemUpdateDTO dto
    ) {
        return ResponseEntity.ok(
                cartItemService.updateQuantity(id, dto.getQuantity())
        );
    }

    @Operation(
            summary = "Excluir item do carrinho",
            description = "Remove um item do carrinho pelo ID (ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
