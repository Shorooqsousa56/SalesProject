package com.example.SalesProject.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddCurrency {
    private String code;
    private String name;
    private String symbol;
    private BigDecimal exchangeRate;

}
