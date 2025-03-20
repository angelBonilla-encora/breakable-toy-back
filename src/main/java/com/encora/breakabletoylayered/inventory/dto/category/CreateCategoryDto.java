package com.encora.breakabletoylayered.inventory.dto.category;

import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateCategoryDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 200, message = "Product name  must be at most 200 characters, and has at least two character")
    private String name;

    private List<ProductEntity> products;
}










