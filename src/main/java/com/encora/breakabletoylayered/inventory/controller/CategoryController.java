package com.encora.breakabletoylayered.inventory.controller;

import com.encora.breakabletoylayered.inventory.dto.category.CategoryMetricsDTO;
import com.encora.breakabletoylayered.inventory.dto.category.CreateCategoryDto;
import com.encora.breakabletoylayered.inventory.dto.category.UpdateCategoryDto;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import com.encora.breakabletoylayered.inventory.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@Tag(name="Category", description = "The Category API contains all the operations that can be performed on a category.")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    public Page<CategoryEntity> findAll(@PageableDefault Pageable pageable){
        return this.categoryService.findAll(pageable);
    }

    @GetMapping("/metrics")
    public List<CategoryMetricsDTO> getMetrics(){
        return this.categoryService.getCategoryMetrics();
    }

    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable UUID id){
        return this.categoryService.findById(id);
    }

    @PostMapping
    public CategoryEntity create(@Valid @RequestBody CreateCategoryDto categoryDto){
        return this.categoryService.createCategory(categoryDto);
    }
    @PutMapping("/{id}")
    public CategoryEntity update( @PathVariable UUID id, @RequestBody UpdateCategoryDto categoryDto){
        System.out.println("categoryDto controller");
        System.out.println(categoryDto);
        return this.categoryService.updateCategory(id, categoryDto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        this.categoryService.deleteCategory(id);
    }
}
