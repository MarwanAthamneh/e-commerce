package org.example.startupprjoect.Service;

import org.example.startupprjoect.model.CartItem;

import java.util.List;

public interface CartService {
    public void addItemToCart(Long userId, Long productId);
    public void deleteCartItem(Long cartId, Long cartItemId, Long userId);
    public List<CartItem> getCartItemsByUserId(Long userId);
}
