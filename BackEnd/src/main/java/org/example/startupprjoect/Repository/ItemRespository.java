package org.example.startupprjoect.Repository;

import org.example.startupprjoect.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRespository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
}
