package com.encora.breakabletoylayered.inventory.dto.category;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryMetricsDTO {
    private String categoryName;
    private Long totalProductsInStock;
    private BigDecimal totalValueInStock;
    private BigDecimal averagePriceInStock;

    public CategoryMetricsDTO(String categoryName, Long totalProductsInStock,
                              BigDecimal totalValueInStock, BigDecimal averagePriceInStock) {
        this.categoryName = categoryName;
        this.totalProductsInStock = totalProductsInStock;
        this.totalValueInStock = totalValueInStock;
        this.averagePriceInStock = averagePriceInStock;
    }
}