package com.encora.breakabletoylayered.inventory.controller;

import com.encora.breakabletoylayered.inventory.dto.product.CreateProductDto;
import com.encora.breakabletoylayered.inventory.dto.product.UpdateProductDto;
import com.encora.breakabletoylayered.inventory.model.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   private UUID categoryId = UUID.fromString("111e8400-e29b-41d4-a716-446655440000");
   private UUID productId;

   @BeforeEach
   void setUp() throws Exception {
      CreateProductDto productDto = new CreateProductDto();
      productDto.setName("Test Product");
      productDto.setCategory_id(categoryId);
      productDto.setPrice(BigDecimal.valueOf(100));
      productDto.setQuantityInStock(10);

      ResultActions result = mockMvc.perform(post("/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(productDto)));

      String response = result.andReturn().getResponse().getContentAsString();
      ProductEntity createdProduct = objectMapper.readValue(response, ProductEntity.class);
      productId = createdProduct.getId();
   }

   @Test
   void shouldCreateProductSuccessfully() throws Exception {
      CreateProductDto productDto = new CreateProductDto();
      productDto.setName("New Product");
      productDto.setCategory_id(categoryId);
      productDto.setPrice(BigDecimal.valueOf(150));
      productDto.setQuantityInStock(5);

      mockMvc.perform(post("/products")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(productDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").exists())
              .andExpect(jsonPath("$.name").value("New Product"))
              .andExpect(jsonPath("$.price").value(150))
              .andExpect(jsonPath("$.quantityInStock").value(5));
   }

   @Test
   void shouldGetAllProducts() throws Exception {
      mockMvc.perform(get("/products"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))));
   }

   @Test
   void shouldGetProductById() throws Exception {
      mockMvc.perform(get("/products/{id}", productId))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(productId.toString()));
   }

   @Test
   void shouldUpdateProduct() throws Exception {
      UpdateProductDto updateDto = new UpdateProductDto();
      updateDto.setName("Updated Product");
      updateDto.setCategory_id(categoryId);
      updateDto.setPrice(BigDecimal.valueOf(200));
      updateDto.setQuantityInStock(20);

      mockMvc.perform(put("/products/{id}", productId)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(updateDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.name").value("Updated Product"))
              .andExpect(jsonPath("$.price").value(200))
              .andExpect(jsonPath("$.quantityInStock").value(20));
   }

   @Test
   void shouldDeleteProduct() throws Exception {
      mockMvc.perform(delete("/products/{id}", productId))
              .andExpect(status().isOk());

      mockMvc.perform(get("/products/{id}", productId))
              .andExpect(status().isNotFound());
   }

   @Test
   void shouldSetProductOutOfStock() throws Exception {
      mockMvc.perform(post("/products/{id}/outofstock", productId))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.quantityInStock").value(0));
   }

   @Test
   void shouldSetProductInStock() throws Exception {
      mockMvc.perform(put("/products/{id}/instock", productId))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.quantityInStock").value(10));
   }
}