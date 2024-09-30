package com.example.springbootcrud.repository;

import com.example.springbootcrud.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to search by name with pagination
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
