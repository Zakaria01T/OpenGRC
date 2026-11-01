package com.grc.opengrc.controller;

import com.grc.opengrc.entity.user.User;
import com.grc.opengrc.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  public record LoginRequest(
      @NotBlank @Size(max = 255) String email,
      @NotBlank @Size(max = 128) String password) {
  }

  public record UserInfo(String email, String fullName, String role) {
  }

  private final AuthenticationManager authenticationManager;
  private final UserRepository users;
  private final SecurityContextHolderStrategy contextStrategy = SecurityContextHolder.getContextHolderStrategy();
  private final SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();

  public AuthController(AuthenticationManager authenticationManager, UserRepository users) {
    this.authenticationManager = authenticationManager;
    this.users = users;
  }

  @PostMapping("/login")
  public UserInfo login(@Valid @RequestBody LoginRequest body,
      HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(body.email().trim(), body.password()));

    // new session id after login, so a pre-login session id cannot be reused
    // (session fixation)
    if (request.getSession(false) != null) {
      request.changeSessionId();
    }

    SecurityContext context = contextStrategy.createEmptyContext();
    context.setAuthentication(auth);
    contextStrategy.setContext(context);
    contextRepository.saveContext(context, request, response);

    return toInfo(auth.getName());
  }

  @GetMapping("/me")
  public UserInfo me(Authentication authentication) {
    return toInfo(authentication.getName());
  }

  // wrong password, unknown email, disabled account: the caller always sees the
  // same answer
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Map<String, Object>> invalidCredentials() {
    return ResponseEntity.status(401).body(Map.of(
        "status", 401, "error", "Unauthorized", "message", "Invalid credentials"));
  }

  private UserInfo toInfo(String email) {
    User u = users.findByEmailIgnoreCase(email)
        .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    return new UserInfo(u.getEmail(), u.getFullName(), u.getRole().name());
  }
}
