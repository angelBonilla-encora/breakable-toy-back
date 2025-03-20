package com.encora.breakabletoylayered.inventory.service;

import com.encora.breakabletoylayered.inventory.dto.product.CreateProductDto;
import com.encora.breakabletoylayered.inventory.dto.product.UpdateProductDto;
import com.encora.breakabletoylayered.inventory.exception.ResourceNotFoundException;
import com.encora.breakabletoylayered.inventory.mapper.product.CreateProductMapper;
import com.encora.breakabletoylayered.inventory.mapper.product.UpdateProductMapper;
import com.encora.breakabletoylayered.inventory.model.Availability;
import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import com.encora.breakabletoylayered.inventory.repository.ProductRepository;
import com.encora.breakabletoylayered.inventory.utils.ProductSpecifications;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CreateProductMapper createProductMapper;
    private final UpdateProductMapper updateProductMapper;

    public ProductEntity createProduct(CreateProductDto createProductDto) {
        ProductEntity createdProductMapped= this.createProductMapper.map(createProductDto);
        ProductEntity createdProduct = this.productRepository.save(createdProductMapped);
        return this.findById(createdProduct.getId());

    }

    public ProductEntity findById(UUID id){
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public Page<ProductEntity> findAll(Pageable pageable, String name, List<String> category, Availability availability) {
        Specification<ProductEntity> spec = Specification.where(ProductSpecifications.hasName(name))
                .and(ProductSpecifications.hasCategory(category));

        if (availability == Availability.inStock) {
            spec = spec.and(ProductSpecifications.hasQuantityInStockGreaterThan(0));
        } else if (availability == Availability.outOfStock) {
            spec = spec.and(ProductSpecifications.hasQuantityInStockEqualTo(0));
        }

        return this.productRepository.findAll(spec, pageable);
    }

    @Transactional
    public ProductEntity updateProduct(UUID id, UpdateProductDto updateProductDto){
        ProductEntity foundedProduct = this.findById(id);
        ProductEntity updatedProductMapped= this.updateProductMapper.map(updateProductDto);
        foundedProduct.setName(updatedProductMapped.getName());
        foundedProduct.setCategory(updatedProductMapped.getCategory());
        foundedProduct.setPrice(updatedProductMapped.getPrice());
        foundedProduct.setExpirationDate(updatedProductMapped.getExpirationDate());
        foundedProduct.setQuantityInStock(updatedProductMapped.getQuantityInStock());

    return this.productRepository.save(foundedProduct);

    }

    public void deleteProduct(UUID id){
        ProductEntity foundedProduct = this.findById(id);
        this.productRepository.delete(foundedProduct);
    }

    public ProductEntity setProductInStock(UUID id){
        ProductEntity foundedProduct = this.findById(id);
        foundedProduct.setQuantityInStock(10);
        return this.productRepository.save(foundedProduct);
    }

    public ProductEntity setProductOutStock(UUID id){
        ProductEntity foundedProduct = this.findById(id);
        foundedProduct.setQuantityInStock(0);
        return this.productRepository.save(foundedProduct);
    }

}
