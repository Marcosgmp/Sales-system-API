package com.sales.system.service.cart;

import com.sales.system.entity.CartItem;
import com.sales.system.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> listAll() {
        return cartItemRepository.findAll();
    }

    @Transactional
    public CartItem updateQuantity(Long id, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    @Transactional
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
