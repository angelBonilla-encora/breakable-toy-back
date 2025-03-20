package com.encora.breakabletoylayered.inventory.dto.product;

import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class CreateProductDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 200, message = "Product name  must be at most 200 characters, and has at least two character")
    private String name;

    @NotNull(message = "Category is mandatory")
    private UUID category_id;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0,message = "Price must be a positive value")
    private BigDecimal price;


    private Date expirationDate;

    @NotNull(message = "Quantity in stock is mandatory")
    @Min(value = 0,message = "Quantity in stock must be a positive value")
    private int quantityInStock;
}
