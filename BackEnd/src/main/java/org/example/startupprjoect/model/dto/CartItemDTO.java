package org.example.startupprjoect.model.dto;

import lombok.Data;
import org.example.startupprjoect.model.CartItem;
import org.example.startupprjoect.model.Item;

@Data
public class CartItemDTO {
    private Long id;
    private Long cartId;
    private Item item;
    private int quantity;

    public static CartItemDTO from(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setCartId(cartItem.getCart().getId());
        dto.setItem(cartItem.getItem());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }
}