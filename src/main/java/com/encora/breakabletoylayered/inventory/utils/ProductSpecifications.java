package com.encora.breakabletoylayered.inventory.utils;

import com.encora.breakabletoylayered.inventory.model.Availability;
import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class ProductSpecifications {
    public static Specification<ProductEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<ProductEntity> hasCategory(List<String> category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            List<String> lowerCategory = category.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            return criteriaBuilder.lower(root.get("category").get("name")).in(lowerCategory);
        };
    }

    public static Specification<ProductEntity> hasQuantityInStockGreaterThan(int quantity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("quantityInStock"), quantity);
    }

    public static Specification<ProductEntity> hasQuantityInStockEqualTo(int quantity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("quantityInStock"), quantity);
    }
}
