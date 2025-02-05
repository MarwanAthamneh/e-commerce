package org.example.startupprjoect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    // Ensure proper JSON serialization
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Cart getCart() {
        return cart;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Item getItem() {
        return item;
    }
}