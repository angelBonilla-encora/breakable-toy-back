package com.encora.breakabletoylayered.inventory.controller;

import com.encora.breakabletoylayered.inventory.dto.product.CreateProductDto;
import com.encora.breakabletoylayered.inventory.dto.product.UpdateProductDto;
import com.encora.breakabletoylayered.inventory.exception.ValidationException;
import com.encora.breakabletoylayered.inventory.model.Availability;
import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import com.encora.breakabletoylayered.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Product", description = "The Product API contains all the operations that can be performed on a product.")
public class ProductController {

    private ProductService productService;

    @GetMapping
    public Page<ProductEntity> findAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) Availability availability) {
        return this.productService.findAll(pageable, name, category, availability);
    }

    @GetMapping("/{id}")
    public ProductEntity findById(@PathVariable UUID id){
        return this.productService.findById(id);
    }

    @PostMapping
    public ProductEntity create(@Valid @RequestBody CreateProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                    .orElse("Validation failed");
            throw new ValidationException(errorMessage);
        }
        return this.productService.createProduct(productDto);
    }
    @PostMapping("/{id}/outofstock")
    public ProductEntity outOfStock(@PathVariable UUID id){
        return this.productService.setProductOutStock(id);
    }

    @PutMapping("/{id}")
    public ProductEntity update( @PathVariable UUID id, @RequestBody UpdateProductDto updateProductDto){
        System.out.println("updateProductDto controller");
        System.out.println(updateProductDto);
        return this.productService.updateProduct(id, updateProductDto);
    }

    @PutMapping("/{id}/instock")
    public ProductEntity inStock( @PathVariable UUID id){
        return this.productService.setProductInStock(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        this.productService.deleteProduct(id);
    }

}
