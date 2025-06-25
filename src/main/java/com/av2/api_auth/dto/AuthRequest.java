package com.av2.api_auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
  @NotBlank(message = "Username é obrigatório")
  private String username;
  
  @NotBlank(message = "Password é obrigatório")
  private String password;

  @NotBlank(message = "Email é obrigatório")
  private String email;
}
