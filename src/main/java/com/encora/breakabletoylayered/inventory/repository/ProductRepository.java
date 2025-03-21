package com.encora.breakabletoylayered.inventory.repository;

import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {
//    Optional<ProductEntity> findById(UUID id);
//    ProductEntity getAllProductsInStock();

}
