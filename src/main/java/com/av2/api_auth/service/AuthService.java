package com.av2.api_auth.service;

import com.av2.api_auth.dto.AuthRequest;
import com.av2.api_auth.dto.AuthResponse;
import com.av2.api_auth.dto.RegisterRequestDTO;
import com.av2.api_auth.model.Role;
import com.av2.api_auth.model.User;
import com.av2.api_auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    JwtService jwtService,
    AuthenticationManager authenticationManager) 
  {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public AuthResponse login(AuthRequest request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );
      
      User user = (User) authentication.getPrincipal();
      String token = jwtService.generateToken(authentication);
      
      return AuthResponse.builder()
        .token(token)
        .username(user.getUsername())
        .role(user.getRole().name())
        .build();
    } catch (AuthenticationException e) {
      throw new RuntimeException("Credenciais inválidas", e);
    }
  }
  
  public AuthResponse register(RegisterRequestDTO request) {

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new RuntimeException("Username já está em uso");
    }
    
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email já está em uso");
    }
    
    User user = User.builder()
      .username(request.getUsername())
      .password(passwordEncoder.encode(request.getPassword()))
      .fullName(request.getFullName())
      .email(request.getEmail())
      .role(Role.USER)
      .build();
    
    userRepository.save(user);
    
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    
    String token = jwtService.generateToken(authentication);
    
    return AuthResponse.builder()
      .token(token)
      .username(user.getUsername())
      .role(user.getRole().name())
      .build();
  }
  
  public AuthResponse authenticate(AuthRequest request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    User user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    String token = jwtService.generateToken(authentication);

    return AuthResponse.builder()
      .token(token)
      .email(user.getEmail())
      .username(user.getUsername())
      .role(user.getRole().name())
      .build();
  }
}
