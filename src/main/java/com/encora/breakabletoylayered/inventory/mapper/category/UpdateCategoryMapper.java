package com.encora.breakabletoylayered.inventory.mapper.category;

import com.encora.breakabletoylayered.inventory.dto.category.UpdateCategoryDto;
import com.encora.breakabletoylayered.inventory.mapper.Mapper;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class UpdateCategoryMapper implements Mapper<UpdateCategoryDto, CategoryEntity> {
    @Override
    public CategoryEntity map(UpdateCategoryDto in) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(in.getName());
        categoryEntity.setProducts(in.getProducts());
        return null;
    }
}
