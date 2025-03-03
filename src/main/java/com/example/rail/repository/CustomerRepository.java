package com.example.rail.repository;

import com.example.rail.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>,
        PagingAndSortingRepository<Customer, Long   > {
}
