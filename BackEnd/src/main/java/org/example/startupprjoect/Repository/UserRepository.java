package org.example.startupprjoect.Repository;

import org.example.startupprjoect.model.UserE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserE, Long> {
    Optional<UserE> findByEmail(String email);
    boolean existsByEmail(String email);

}
