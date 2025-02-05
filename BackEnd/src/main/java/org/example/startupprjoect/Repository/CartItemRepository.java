package org.example.startupprjoect.Repository;

import org.example.startupprjoect.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository  extends JpaRepository<CartItem,Long> {
}
