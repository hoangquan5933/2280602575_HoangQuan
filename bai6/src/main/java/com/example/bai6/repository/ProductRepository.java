package com.example.bai6.repository;

import com.example.bai6.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Search
    List<Product> findByNameContaining(String keyword);

    // Filter category
    List<Product> findByCategoryId(Long categoryId);
}