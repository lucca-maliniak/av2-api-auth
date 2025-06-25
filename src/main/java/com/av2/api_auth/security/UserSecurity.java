package com.av2.api_auth.security;

import com.av2.api_auth.model.User;
import com.av2.api_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSecurity {

  private final UserRepository userRepository;

  public boolean isUserSelf(Authentication authentication, Long userId) {
      String email = authentication.getName();
      User user = userRepository.findByEmail(email).orElse(null);
      return user != null && user.getId().equals(userId);
  }
}
