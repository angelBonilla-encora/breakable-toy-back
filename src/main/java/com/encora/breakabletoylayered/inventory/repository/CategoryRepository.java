package com.encora.breakabletoylayered.inventory.repository;

import com.encora.breakabletoylayered.inventory.dto.category.CategoryMetricsDTO;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    @Query(value = "SELECT " +
            "c.name AS categoryName, " +
            "SUM(p.quantity_in_stock) AS totalProductsInStock, " +
            "ROUND(SUM(p.quantity_in_stock * p.price),2) AS totalValueInStock, " +
            "ROUND(AVG(p.price),2) AS averagePriceInStock " +
            "FROM categories c " +
            "JOIN products p ON c.id = p.category_id " +
            "GROUP BY c.id " +
            "UNION ALL " +
            "SELECT " +
            "'Overall' AS categoryName, " +
            "SUM(p.quantity_in_stock) AS totalProductsInStock, " +
            "ROUND(SUM(p.quantity_in_stock * p.price),2) AS totalValueInStock, " +
            "ROUND(AVG(p.price),2) AS averagePriceInStock " +
            "FROM products p", nativeQuery = true)
    List<CategoryMetricsDTO> getCategoryMetrics();
}
