package com.sales.system.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CartResponseDTO {

    private Long cartId;
    private List<CartItemResponseDTO> items;
    private BigDecimal total;

}
