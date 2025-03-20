package com.encora.breakabletoylayered.inventory.service;

import com.encora.breakabletoylayered.inventory.dto.category.CategoryMetricsDTO;
import com.encora.breakabletoylayered.inventory.dto.category.CreateCategoryDto;
import com.encora.breakabletoylayered.inventory.dto.category.UpdateCategoryDto;
import com.encora.breakabletoylayered.inventory.mapper.category.CreateCategoryMapper;
import com.encora.breakabletoylayered.inventory.mapper.category.UpdateCategoryMapper;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import com.encora.breakabletoylayered.inventory.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor

public class CategoryService {
    private CategoryRepository categoryRepository;
    private final CreateCategoryMapper createCategoryMapper;
    private final UpdateCategoryMapper updateCategoryMapper;

    public CategoryEntity createCategory(CreateCategoryDto createCategoryDto) {
        CategoryEntity createdCategoryMapped = this.createCategoryMapper.map(createCategoryDto);
        CategoryEntity createdCategory = this.categoryRepository.save(createdCategoryMapped);
        return this.findById(createdCategory.getId());
    }

    public CategoryEntity findById(UUID id) {
        Optional<CategoryEntity> categoryEntity = this.categoryRepository.findById(id);
        if (categoryEntity.isPresent()) {
            return categoryEntity.get();
        }
        throw new NoSuchElementException("Category with id " + id + " not found");
    }

    public Page<CategoryEntity> findAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }
    @Transactional
    public CategoryEntity updateCategory(UUID id, UpdateCategoryDto updateCategoryDto) {
        CategoryEntity foundedCategory = this.findById(id);
        CategoryEntity updatedCategoryMapped = this.updateCategoryMapper.map(updateCategoryDto);
        foundedCategory.setName(updatedCategoryMapped.getName());
        foundedCategory.setProducts(updatedCategoryMapped.getProducts());
        return this.categoryRepository.save(foundedCategory);
    }
    public void deleteCategory(UUID id) {
        CategoryEntity foundedCategory = this.findById(id);
        this.categoryRepository.deleteById(id);
    }

    public List<CategoryMetricsDTO> getCategoryMetrics(){
        return this.categoryRepository.getCategoryMetrics();
    }
}
