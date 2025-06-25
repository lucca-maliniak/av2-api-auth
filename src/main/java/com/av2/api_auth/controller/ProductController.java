package com.av2.api_auth.controller;

import com.av2.api_auth.dto.ProductDTO;
import com.av2.api_auth.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @Operation(summary = "Listar todos os produtos", description = "Retorna todos os produtos cadastrados")
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
      return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
      return ResponseEntity.ok(productService.getProductById(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Criar produto", description = "Cria um novo produto (apenas ADMIN)")
  public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
      return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente (apenas ADMIN)")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
      return ResponseEntity.ok(productService.updateProduct(id, productDTO));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Excluir produto", description = "Exclui um produto pelo ID (apenas ADMIN)")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
      productService.deleteProduct(id);
      return ResponseEntity.noContent().build();
  }
}
