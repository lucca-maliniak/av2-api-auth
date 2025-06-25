package com.av2.api_auth.controller;

import com.av2.api_auth.dto.AuthRequest;
import com.av2.api_auth.dto.AuthResponse;
import com.av2.api_auth.dto.RegisterRequestDTO;
import com.av2.api_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  @Operation(summary = "Autenticar usuário", description = "Autentica um usuário e retorna um token JWT")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/register")
  @Operation(summary = "Registrar novo usuário", description = "Registra um novo usuário e retorna um token JWT")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequestDTO request) {
    return ResponseEntity.ok(authService.register(request));
  }
}
