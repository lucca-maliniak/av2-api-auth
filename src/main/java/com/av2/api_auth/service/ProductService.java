package com.av2.api_auth.service;

import com.av2.api_auth.dto.ProductDTO;
import com.av2.api_auth.model.Product;
import com.av2.api_auth.exception.ResourceNotFoundException;
import com.av2.api_auth.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

private final ProductRepository productRepository;

public List<ProductDTO> getAllProducts() {
  return productRepository.findAll().stream()
    .map(this::mapToDTO)
    .collect(Collectors.toList());
}

public ProductDTO getProductById(Long id) {
  Product product = productRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
  return mapToDTO(product);
}

@Transactional
public ProductDTO createProduct(ProductDTO productDTO) {
  Product product = mapToEntity(productDTO);
  Product savedProduct = productRepository.save(product);
  return mapToDTO(savedProduct);
}

@Transactional
public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
  Product existingProduct = productRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
  
  existingProduct.setName(productDTO.getName());
  existingProduct.setDescription(productDTO.getDescription());
  existingProduct.setPrice(productDTO.getPrice());
  existingProduct.setCategory(productDTO.getCategory());
  
  Product updatedProduct = productRepository.save(existingProduct);
  return mapToDTO(updatedProduct);
}

@Transactional
public void deleteProduct(Long id) {
  if (!productRepository.existsById(id)) {
    throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
  }
  productRepository.deleteById(id);
}

public List<ProductDTO> getProductsByCategory(String category) {
  return productRepository.findByCategory(category).stream()
    .map(this::mapToDTO)
    .collect(Collectors.toList());
}

private ProductDTO mapToDTO(Product product) {
  return ProductDTO.builder()
    .id(product.getId())
    .name(product.getName())
    .description(product.getDescription())
    .price(product.getPrice())
    .category(product.getCategory())
    .build();
}

private Product mapToEntity(ProductDTO productDTO) {
  return Product.builder()
    .id(productDTO.getId())
    .name(productDTO.getName())
    .description(productDTO.getDescription())
    .price(productDTO.getPrice())
    .category(productDTO.getCategory())
    .build();
}
}
