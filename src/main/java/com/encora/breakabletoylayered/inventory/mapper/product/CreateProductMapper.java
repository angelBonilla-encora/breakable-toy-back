package com.encora.breakabletoylayered.inventory.mapper.product;

import com.encora.breakabletoylayered.inventory.dto.product.CreateProductDto;
import com.encora.breakabletoylayered.inventory.mapper.Mapper;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import com.encora.breakabletoylayered.inventory.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateProductMapper implements Mapper<CreateProductDto, ProductEntity> {
    private final CategoryRepository categoryRepository;
    @Override
    public ProductEntity map(CreateProductDto in) {
        CategoryEntity category = categoryRepository.findById(in.getCategory_id()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(in.getName());
        productEntity.setCategory(category);
        productEntity.setPrice(in.getPrice());
        productEntity.setExpirationDate(in.getExpirationDate());
        productEntity.setQuantityInStock(in.getQuantityInStock());

        return productEntity;
    }
}
