package com.encora.breakabletoylayered.inventory.mapper.category;

import com.encora.breakabletoylayered.inventory.dto.category.CreateCategoryDto;
import com.encora.breakabletoylayered.inventory.mapper.Mapper;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryMapper implements Mapper<CreateCategoryDto, CategoryEntity> {

    @Override
    public CategoryEntity map(CreateCategoryDto in) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(in.getName());
        categoryEntity.setProducts(in.getProducts());
        return categoryEntity;
    }
}
