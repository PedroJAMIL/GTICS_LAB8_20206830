package com.example.laboratorio8.repository;

import com.example.laboratorio8.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // JpaRepository ya proporciona todos los métodos necesarios
}
