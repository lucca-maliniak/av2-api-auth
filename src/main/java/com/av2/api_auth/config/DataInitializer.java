package com.av2.api_auth.config;

import com.av2.api_auth.model.Product;
import com.av2.api_auth.model.Role;
import com.av2.api_auth.model.User;
import com.av2.api_auth.repository.ProductRepository;
import com.av2.api_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public CommandLineRunner initData() {
    return args -> {
      if (userRepository.count() == 0) {
        User admin = User.builder()
          .username("Admin User")
          .email("admin@example.com")
          .password(passwordEncoder.encode("admin123"))
          .fullName("Administrador")
          .role(Role.ADMIN)
          .build();

        User user = User.builder()
          .username("Regular User")
          .email("user@example.com")
          .password(passwordEncoder.encode("user123"))
          .fullName("Administrador")
          .role(Role.USER)
          .build();

        userRepository.saveAll(Arrays.asList(admin, user));
        System.out.println("Usuários de exemplo criados!");
      }

      // Criar produtos de exemplo
      if (productRepository.count() == 0) {
        Product product1 = Product.builder()
          .name("Smartphone XYZ")
          .description("Um smartphone de última geração com câmera de alta resolução")
          .price(new BigDecimal("1299.99"))
          .category("Electronics")
          .build();

        Product product2 = Product.builder()
          .name("Notebook ABC")
          .description("Notebook leve e potente para trabalho e entretenimento")
          .price(new BigDecimal("3499.99"))
          .category("Electronics")
          .build();

        Product product3 = Product.builder()
          .name("Headphone Wireless")
          .description("Fones de ouvido sem fio com cancelamento de ruído")
          .price(new BigDecimal("499.99"))
          .category("Accessories")
          .build();

        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        System.out.println("Produtos de exemplo criados!");
      }
    };
  }
}
