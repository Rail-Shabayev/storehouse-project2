package com.example.rail.repository;

import com.example.rail.model.Product;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("dev")
public class ProductRepositoryTest extends ContainerStart {
    @Autowired
    private ProductRepository productRepository;

    Product product = Instancio.of(Product.class).create();

    @Test
    @DisplayName("should find a product by it's UUID")
    public void shouldFindById() {
        //Arrange
        productRepository.deleteAll();
        productRepository.save(product);
        //Act
        List<Product> actualProduct = productRepository.findAll();
        //Assert
        assertThat(actualProduct.get(0)
                .getName()).isEqualTo(product.getName());
    }
    @Test
    @DisplayName("should find a product using criteriaApi")
    public void shouldFindByCriteria() {
        //Arrange
        productRepository.save(product);
        Specification<Product> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), product.getName());
        //Act
        String  fetchedProductName = productRepository.findAll(specification)
                .get(0)
                .getName();
        //Assert
        assertThat(fetchedProductName).isEqualTo(product.getName());
        assertThat(fetchedProductName).isNotEqualTo("some name");
    }
}