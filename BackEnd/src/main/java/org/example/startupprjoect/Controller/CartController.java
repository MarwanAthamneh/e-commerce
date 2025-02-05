package org.example.startupprjoect.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.startupprjoect.Service.ServiceImpl.CartServiceImpl;
import org.example.startupprjoect.model.CartItem;
import org.example.startupprjoect.model.dto.CartItemDTO;
import org.example.startupprjoect.security.JWTGenrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CartController {
    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private JWTGenrator jwtGenrator;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartItemsByUserId(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Fetching cart for user: " + userId);
            String jwt = token.substring(7);
            Long tokenUserId = jwtGenrator.extractUserId(jwt);

            if (!userId.equals(tokenUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied");
            }

            List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
            List<CartItemDTO> dtos = cartItems.stream()
                    .map(CartItemDTO::from)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cart items: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestParam Long productId,
            @RequestHeader("Authorization") String tokenHeader) {
        try {
            System.out.println("Adding product to cart. Product ID: " + productId);
            System.out.println("Authorization header: " + tokenHeader);

            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid Authorization header format");
            }

            String token = tokenHeader.substring(7);
            if (!jwtGenrator.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid token");
            }

            Long userId = jwtGenrator.extractUserId(token);
            System.out.println("User ID from token: " + userId);

            cartService.addItemToCart(userId, productId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Product added to cart successfully!");
            response.put("userId", userId);
            response.put("productId", productId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error adding item to cart: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding item to cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Deleting cart item - CartID: " + cartId + ", ItemID: " + cartItemId);

            String jwt = token.substring(7);
            Long userId = jwtGenrator.extractUserId(jwt);

            System.out.println("User ID from token: " + userId);

            cartService.deleteCartItem(cartId, cartItemId, userId);
            return ResponseEntity.ok("Item removed successfully");
        } catch (Exception e) {
            System.err.println("Error removing item: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove item: " + e.getMessage());
        }
    }

    @PutMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId,
            @RequestParam int quantity,
            @RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7);
            Long userId = jwtGenrator.extractUserId(jwt);

            cartService.updateCartItemQuantity(cartId, cartItemId, quantity, userId);
            return ResponseEntity.ok("Cart item quantity updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update cart item: " + e.getMessage());
        }
    }
}

