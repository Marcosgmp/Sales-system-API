package com.sales.system.service.cart;

import com.sales.system.dto.cart.CartItemResponseDTO;
import com.sales.system.dto.cart.CartResponseDTO;
import com.sales.system.entity.Cart;
import com.sales.system.entity.CartItem;
import com.sales.system.entity.Product;
import com.sales.system.entity.User;
import com.sales.system.repository.CartItemRepository;
import com.sales.system.repository.CartRepository;
import com.sales.system.repository.ProductRepository;
import com.sales.system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public CartResponseDTO getCartResponse(String email) {
        Cart cart = getCartByEmail(email);
        return mapToDTO(cart);
    }

    @Transactional
    public CartResponseDTO addItem(String email, Long productId, int quantity) {
        Cart cart = getCartByEmail(email);
        addItemRaw(cart, productId, quantity);
        return mapToDTO(cart);
    }

    @Transactional
    public CartResponseDTO updateQuantity(String email, Long productId, int quantity) {
        Cart cart = getCartByEmail(email);
        updateQuantityRaw(cart, productId, quantity);
        return mapToDTO(cart);
    }

    @Transactional
    public CartResponseDTO removeItem(String email, Long productId) {
        Cart cart = getCartByEmail(email);
        updateQuantityRaw(cart, productId, 0);
        return mapToDTO(cart);
    }

    public BigDecimal getTotal(String email) {
        return getCartResponse(email).getTotal();
    }

    @Transactional
    public void clearCart(String email) {
        Cart cart = getCartByEmail(email);
        cart.getItems().forEach(cartItemRepository::delete);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart getCartByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCart() == null) {
            throw new IllegalStateException("Cart not initialized");
        }
        return user.getCart();
    }

    private void addItemRaw(Cart cart, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }

        cartRepository.save(cart);
    }

    private void updateQuantityRaw(Cart cart, Long productId, int quantity) {
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
        }

        cartRepository.save(cart);
    }

    private CartResponseDTO mapToDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getId());

        List<CartItemResponseDTO> items = cart.getItems().stream().map(item -> {
            CartItemResponseDTO itemDto = new CartItemResponseDTO();
            itemDto.setItemId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setUnitPrice(item.getProduct().getPrice());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setSubtotal(
                    item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            return itemDto;
        }).toList();

        dto.setItems(items);
        dto.setTotal(
                items.stream()
                        .map(CartItemResponseDTO::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return dto;
    }
}
