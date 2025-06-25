package com.av2.api_auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {

  private final JwtEncoder jwtEncoder;
  
  @Value("${jwt.expiration}")
  private long jwtExpiration;

  public JwtService(JwtEncoder jwtEncoder) {
      this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(Authentication authentication) {
      Instant now = Instant.now();
      
      String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
      
      JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(jwtExpiration, ChronoUnit.MILLIS))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
      
      return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}