package com.av2.api_auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  private Long id;
  
  @NotBlank(message = "Nome é obrigatório")
  private String name;
  
  private String description;
  
  @Positive(message = "Preço deve ser maior que zero")
  private BigDecimal price;
  
  private String category;
}
