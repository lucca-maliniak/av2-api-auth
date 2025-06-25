package com.av2.api_auth.service;

import com.av2.api_auth.dto.UserDTO;
import com.av2.api_auth.model.User;
import com.av2.api_auth.exception.ResourceNotFoundException;
import com.av2.api_auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
  }

  public List<UserDTO> getAllUsers() {
      return userRepository.findAll().stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
  }

  public UserDTO getUserById(Long id) {
      User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
      return mapToDTO(user);
  }

  public UserDTO getUserByUsername(String username) {
      User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com username: " + username));
      return mapToDTO(user);
  }

  private UserDTO mapToDTO(User user) {
      return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .fullName(user.getFullName())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }
}
