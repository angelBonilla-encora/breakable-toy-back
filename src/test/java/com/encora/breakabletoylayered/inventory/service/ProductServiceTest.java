package com.encora.breakabletoylayered.inventory.service;

import com.encora.breakabletoylayered.inventory.dto.product.CreateProductDto;
import com.encora.breakabletoylayered.inventory.dto.product.UpdateProductDto;
import com.encora.breakabletoylayered.inventory.exception.ResourceNotFoundException;
import com.encora.breakabletoylayered.inventory.mapper.product.CreateProductMapper;
import com.encora.breakabletoylayered.inventory.mapper.product.UpdateProductMapper;
import com.encora.breakabletoylayered.inventory.model.Availability;
import com.encora.breakabletoylayered.inventory.model.CategoryEntity;
import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import com.encora.breakabletoylayered.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CreateProductMapper createProductMapper;

    @Mock
    private UpdateProductMapper updateProductMapper;

    @InjectMocks
    private ProductService productService;

    private UUID productId;
    private ProductEntity productEntity;
    private CategoryEntity categoryEntity;
    private CreateProductDto createProductDto;
    private UpdateProductDto updateProductDto;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        categoryEntity = new CategoryEntity();
        categoryEntity.setId(UUID.randomUUID());
        categoryEntity.setName("test");

        productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setName("Test Product");
        productEntity.setCategory(categoryEntity);
        productEntity.setPrice(new BigDecimal("99.99"));
        productEntity.setQuantityInStock(5);

        createProductDto = new CreateProductDto();
        updateProductDto = new UpdateProductDto();
    }

    @Test
    void testCreateProduct() {
        when(createProductMapper.map(createProductDto)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        ProductEntity createdProduct = productService.createProduct(createProductDto);

        assertNotNull(createdProduct);
        assertEquals(productEntity.getId(), createdProduct.getId());
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void testFindById_ProductExists() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        ProductEntity foundProduct = productService.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindById_ProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(productId));
    }

    @Test
    void testUpdateProduct() {
        ProductEntity updatedEntity = new ProductEntity();
        updatedEntity.setName("Updated Name");
        updatedEntity.setCategory(categoryEntity);
        updatedEntity.setPrice(new BigDecimal("199.99"));
        updatedEntity.setQuantityInStock(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(updateProductMapper.map(updateProductDto)).thenReturn(updatedEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedEntity);

        ProductEntity updatedProduct = productService.updateProduct(productId, updateProductDto);

        assertEquals("Updated Name", updatedProduct.getName());
        assertEquals(categoryEntity, updatedProduct.getCategory());
        assertEquals(new BigDecimal("199.99"), updatedProduct.getPrice());
        assertEquals(10, updatedProduct.getQuantityInStock());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        doNothing().when(productRepository).delete(productEntity);

        assertDoesNotThrow(() -> productService.deleteProduct(productId));

        verify(productRepository, times(1)).delete(productEntity);
    }

    @Test
    void testSetProductInStock() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductEntity updatedProduct = productService.setProductInStock(productId);

        assertEquals(10, updatedProduct.getQuantityInStock());
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void testSetProductOutStock() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductEntity updatedProduct = productService.setProductOutStock(productId);

        assertEquals(0, updatedProduct.getQuantityInStock());
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10); // Usa un pageable real en lugar de un mock
        Page<ProductEntity> mockPage = mock(Page.class);

        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);

        Page<ProductEntity> result = productService.findAll(pageable, "Test", List.of("Electronics"), Availability.inStock);

        assertNotNull(result);
        verify(productRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }
}