package com.example.rail.dto.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerInfo {
    private Long id;
    private String accountNumber;
    private String email;
    private String inn;
}
