package com.encora.breakabletoylayered.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table(name = "products")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 200, message = "Product name  must be at most 200 characters, and has at least two character")
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private CategoryEntity category;

    @Min(value = 0,message = "Price must be a positive value")
    private BigDecimal price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expirationDate;

    @Min(value = 0,message = "Quantity in stock must be a positive value")
    private int quantityInStock;

    @CreatedDate()
    private Date createdAt;
    @LastModifiedDate()
    private Date updatedAt;
}

