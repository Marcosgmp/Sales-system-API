package com.sales.system.service;

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
    public CartItem update(Long id, CartItem data) {
        CartItem existing = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        existing.setQuantity(data.getQuantity());
        return cartItemRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
