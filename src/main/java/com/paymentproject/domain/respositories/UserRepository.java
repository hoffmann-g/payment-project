package com.paymentproject.domain.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentproject.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocument(String document);
    
    boolean existsById(Long id);
    boolean existsByDocument(String document);


}
