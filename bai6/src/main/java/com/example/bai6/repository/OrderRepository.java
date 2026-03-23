package com.example.bai6.repository;

import com.example.bai6.entity.Order; // ✅ THÊM DÒNG NÀY
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}