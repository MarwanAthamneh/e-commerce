package org.example.startupprjoect.Repository;

import org.example.startupprjoect.model.Cart;
import org.example.startupprjoect.model.UserE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserE(UserE user);
}
