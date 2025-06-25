package com.av2.api_auth.service.tests;

import com.av2.api_auth.dto.AuthRequest;
import com.av2.api_auth.dto.AuthResponse;
import com.av2.api_auth.dto.RegisterRequestDTO;
import com.av2.api_auth.model.Role;
import com.av2.api_auth.model.User;
import com.av2.api_auth.repository.UserRepository;
import com.av2.api_auth.service.AuthService;
import com.av2.api_auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private Authentication authentication;

  @InjectMocks
  private AuthService authService;

  private RegisterRequestDTO registerRequest;
  private AuthRequest authRequest;
  private User user;

  @BeforeEach
  void setUp() {
    registerRequest = new RegisterRequestDTO("testuser",  "password", "Test User", "test@example.com");
    authRequest = new AuthRequest("test@example.com", "password");
    
    user = User.builder()
      .id(1L)
      .username("Test User")
      .email("test@example.com")
      .password("encodedPassword")
      .role(Role.USER)
      .build();
  }

  @Test
  void testRegister_Success() {
    // Arrange
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

    // Act
    AuthResponse response = authService.register(registerRequest);

    // Assert
    assertNotNull(response);
    assertEquals("jwtToken", response.getToken());
    assertEquals("test@example.com", response.getEmail());
    assertEquals("Test User", response.getUsername());
    assertEquals("ROLE_USER", response.getRole());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testRegister_UserAlreadyExists() {
    // Arrange
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    // Act & Assert
    assertThrows(RuntimeException.class, () -> {
      authService.register(registerRequest);
    });
    
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void testAuthenticate_Success() {
    // Arrange
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

    // Act
    AuthResponse response = authService.authenticate(authRequest);

    // Assert
    assertNotNull(response);
    assertEquals("jwtToken", response.getToken());
    assertEquals("test@example.com", response.getEmail());
    assertEquals("Test User", response.getUsername());
    assertEquals("ROLE_USER", response.getRole());
    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  void testAuthenticate_UserNotFound() {
    // Arrange
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> {
      authService.authenticate(authRequest);
    });
  }
}
