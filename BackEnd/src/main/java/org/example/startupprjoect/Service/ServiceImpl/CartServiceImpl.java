package org.example.startupprjoect.Service.ServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.startupprjoect.Repository.CartItemRepository;
import org.example.startupprjoect.Repository.CartRepository;
import org.example.startupprjoect.Repository.ItemRespository;
import org.example.startupprjoect.Repository.UserRepository;
import org.example.startupprjoect.Service.CartService;
import org.example.startupprjoect.model.Cart;
import org.example.startupprjoect.model.CartItem;
import org.example.startupprjoect.model.Item;
import org.example.startupprjoect.model.UserE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRespository itemRespository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        System.out.println("Fetching cart items for userId: " + userId);


        UserE user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        Cart cart = cartRepository.findByUserE(user)
                .orElseGet(() -> {
                    System.out.println("Creating new cart for user: " + userId);
                    Cart newCart = new Cart();
                    newCart.setUserE(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        List<CartItem> items = cart.getItems();
        System.out.println("Found " + items.size() + " items in cart " + cart.getId());

        items.forEach(item -> {
            if (item != null && item.getItem() != null) {
                System.out.println("Item: " + item.getItem().getName() +
                        ", Quantity: " + item.getQuantity() +
                        ", Price: " + item.getItem().getPrice());
            }
        });

        return items;
    }

    @Override
    public void addItemToCart(Long userId, Long itemId) {
        System.out.println("Adding item " + itemId + " to cart for user " + userId);

        UserE user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Item item = itemRespository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));


        Cart cart = cartRepository.findByUserE(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserE(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });


        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(ci -> ci.getItem().getId().equals(itemId))
                .findFirst();

        if (existingCartItem.isPresent()) {

            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
            System.out.println("Updated quantity for item " + itemId);
        } else {

            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setItem(item);
            newCartItem.setQuantity(1);
            cart.getItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
            System.out.println("Added new item " + itemId + " to cart");
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCartItem(Long cartId, Long cartItemId, Long userId) {
        System.out.println("Deleting item " + cartItemId + " from cart " + cartId + " for user " + userId);

        // Find the cart and verify ownership
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (!cart.getUserE().getId().equals(userId)) {
            throw new AccessDeniedException("Not authorized to modify this cart");
        }

        // Find the cart item
        CartItem itemToRemove = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        // Verify the item belongs to the specified cart
        if (!itemToRemove.getCart().getId().equals(cartId)) {
            throw new IllegalArgumentException("Item does not belong to the specified cart");
        }

        // Remove the item from the cart's items collection
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));

        // Delete the cart item
        cartItemRepository.delete(itemToRemove);

        // Save the cart to persist changes
        cartRepository.save(cart);

        System.out.println("Successfully deleted cart item");
    }

    public void updateCartItemQuantity(Long cartId, Long cartItemId, int quantity, Long userId) {
    }
}

