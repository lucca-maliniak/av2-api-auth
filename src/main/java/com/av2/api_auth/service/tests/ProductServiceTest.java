package com.av2.api_auth.service.tests;

import com.av2.api_auth.dto.ProductDTO;
import com.av2.api_auth.model.Product;
import com.av2.api_auth.exception.ResourceNotFoundException;
import com.av2.api_auth.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  private Product product;
  private ProductDTO productDTO;

  @BeforeEach
  void setUp() {
      product = Product.builder()
              .id(1L)
              .name("Test Product")
              .description("Test Description")
              .price(new BigDecimal("99.99"))
              .category("Electronics")
              .build();

      productDTO = ProductDTO.builder()
              .id(1L)
              .name("Test Product")
              .description("Test Description")
              .price(new BigDecimal("99.99"))
              .category("Electronics")
              .build();
  }

  @Test
  void testGetAllProducts() {
      when(productRepository.findAll()).thenReturn(Arrays.asList(product));

      List<ProductDTO> result = productService.getAllProducts();

      assertNotNull(result);
      assertEquals(1, result.size());
      assertEquals("Test Product", result.get(0).getName());
  }

  @Test
  void testGetProductById() {
      when(productRepository.findById(1L)).thenReturn(Optional.of(product));

      ProductDTO result = productService.getProductById(1L);

      assertNotNull(result);
      assertEquals("Test Product", result.getName());
  }

  @Test
  void testGetProductById_NotFound() {
      when(productRepository.findById(1L)).thenReturn(Optional.empty());

      assertThrows(ResourceNotFoundException.class, () -> {
          productService.getProductById(1L);
      });
  }

  @Test
  void testCreateProduct() {
      when(productRepository.save(any(Product.class))).thenReturn(product);

      ProductDTO result = productService.createProduct(productDTO);

      assertNotNull(result);
      assertEquals("Test Product", result.getName());
      verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  void testUpdateProduct() {
      when(productRepository.findById(1L)).thenReturn(Optional.of(product));
      when(productRepository.save(any(Product.class))).thenReturn(product);

      ProductDTO updatedDTO = productDTO;
      updatedDTO.setName("Updated Product");

      ProductDTO result = productService.updateProduct(1L, updatedDTO);

      assertNotNull(result);
      verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  void testUpdateProduct_NotFound() {
      when(productRepository.findById(1L)).thenReturn(Optional.empty());

      assertThrows(ResourceNotFoundException.class, () -> {
          productService.updateProduct(1L, productDTO);
      });
  }

  @Test
  void testDeleteProduct() {
      when(productRepository.existsById(1L)).thenReturn(true);
      doNothing().when(productRepository).deleteById(1L);

      productService.deleteProduct(1L);

      verify(productRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteProduct_NotFound() {
      when(productRepository.existsById(1L)).thenReturn(false);

      assertThrows(ResourceNotFoundException.class, () -> {
          productService.deleteProduct(1L);
      });
  }

  @Test
  void testGetProductsByCategory() {
      when(productRepository.findByCategory("Electronics")).thenReturn(Arrays.asList(product));

      List<ProductDTO> result = productService.getProductsByCategory("Electronics");

      assertNotNull(result);
      assertEquals(1, result.size());
      assertEquals("Electronics", result.get(0).getCategory());
  }
}
