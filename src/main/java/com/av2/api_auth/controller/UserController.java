package com.av2.api_auth.controller;

import com.av2.api_auth.dto.UserDTO;
import com.av2.api_auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Listar todos os usuários", description = "Lista todos os usuários (requer ADMIN)")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or authentication.principal.username == @userService.getUserById(#id).username")
  @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário pelo ID (requer ADMIN ou ser o próprio usuário)")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping("/me")
  @Operation(summary = "Buscar usuário atual", description = "Busca informações do usuário autenticado")
  public ResponseEntity<UserDTO> getCurrentUser(@RequestParam String username) {
    return ResponseEntity.ok(userService.getUserByUsername(username));
  }
}
