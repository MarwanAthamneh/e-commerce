package org.example.startupprjoect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String imageUrl;
    private Integer stockQuantity;


    public Item(String name, double price, String imageUrl ,Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stockQuantity=stockQuantity;

    }

    public boolean hasEnoughStock(int requestedQuantity) {
        return stockQuantity != null && stockQuantity >= requestedQuantity;
    }
}
