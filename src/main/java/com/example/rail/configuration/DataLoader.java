package com.example.rail.configuration;

import com.example.rail.model.Product;
import com.example.rail.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.rail.model.Category.*;
import static java.util.UUID.randomUUID;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product
                    (randomUUID(), "laptop", 1L, "gaming laptop", DEVICES,
                            new BigDecimal(9999), 2, LocalDateTime.now(), LocalDate.now());
            Product product2 = new Product
                    (randomUUID(), "snikers", 2L, "best snikers", CLOTHES,
                            new BigDecimal(293), 5, LocalDateTime.now(), LocalDate.now());
            Product product3 = new Product
                    (randomUUID(), "can't hurt me", 3L, "stay hard son", BOOKS,
                            new BigDecimal(2883), 10, LocalDateTime.now(), LocalDate.now());
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        };
    }
}
