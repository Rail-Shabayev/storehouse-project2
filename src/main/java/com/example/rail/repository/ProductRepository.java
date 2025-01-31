package com.example.rail.repository;

import com.example.rail.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, PagingAndSortingRepository<Product, UUID> {
    @Query("select (count(p) > 0) from Product p where p.article = ?1")
    boolean existsByArticle(Long article);
}
