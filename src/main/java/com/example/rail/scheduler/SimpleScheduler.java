package com.example.rail.scheduler;


import com.example.rail.annotation.TrackExecutionTime;
import com.example.rail.model.Product;
import com.example.rail.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!local")
@ConditionalOnProperty(prefix = "app.scheduling", value = "enabled")
@ConditionalOnMissingBean(OptimizedScheduler.class)
public class SimpleScheduler {
    private final ProductRepository productRepository;

    @Value("${app.scheduling.priceIncreasePercentage}")
    private BigDecimal percentage;

    @Scheduled(fixedDelayString = "${app.scheduling.fixedDelay}")
    @Transactional
    @TrackExecutionTime
    public void simpleIncreasePriceScheduler() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            BigDecimal price = product.getPrice();
            price = price.multiply(BigDecimal.ONE.add(percentage.
                    divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)));
            product.setPrice(price);
        });
        productRepository.saveAll(products);
    }
}
