package com.example.demo.web.dao;

import com.example.demo.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findById(int id);
    List<Product> findByPrixGreaterThan(int prixLimit);

    @Query("SELECT id, name, prix FROM Product p WHERE p.prix > :limit")
    List<Product> findExpensiveProduct(@Param("limit") int limit);
}
