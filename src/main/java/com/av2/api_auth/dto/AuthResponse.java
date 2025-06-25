package com.av2.api_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
  private String token;
  private String type = "Bearer";
  private Long userId;
  private String username;
  private String email;
  private String role;
  
  public AuthResponse(String token, Long userId, String username, String email, String role) {
      this.token = token;
      this.userId = userId;
      this.username = username;
      this.email = email;
      this.role = role;
  }
}
