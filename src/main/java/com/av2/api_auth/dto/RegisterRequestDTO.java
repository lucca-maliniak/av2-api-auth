package com.av2.api_auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
  @NotBlank(message = "Username é obrigatório")
  @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
  private String username;
  
  @NotBlank(message = "Password é obrigatório")
  @Size(min = 6, message = "Password deve ter no mínimo 6 caracteres")
  private String password;
  
  @NotBlank(message = "Nome completo é obrigatório")
  private String fullName;
  
  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email deve ser válido")
  private String email;
}
