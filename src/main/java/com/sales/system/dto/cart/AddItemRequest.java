package com.sales.system.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddItemRequest {

    private Long productId;
    private int quantity;

}
