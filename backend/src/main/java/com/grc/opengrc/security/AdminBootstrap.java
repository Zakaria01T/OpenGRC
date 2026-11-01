package com.grc.opengrc.security;

import com.grc.opengrc.entity.user.Role;
import com.grc.opengrc.entity.user.User;
import com.grc.opengrc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {

  private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final String email;
  private final String password;

  public AdminBootstrap(UserRepository users, PasswordEncoder encoder,
      @Value("${app.admin.email}") String email,
      @Value("${app.admin.password}") String password) {
    this.users = users;
    this.encoder = encoder;
    this.email = email;
    this.password = password;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (email.isBlank() || password.isBlank()) {
      log.warn("ADMIN_EMAIL or ADMIN_PASSWORD not set: no initial administrator created");
      return;
    }
    if (password.length() < 12) {
      throw new IllegalStateException("ADMIN_PASSWORD must have at least 12 characters");
    }
    if (users.existsByEmailIgnoreCase(email)) {
      return;
    }
    User admin = new User();
    admin.setEmail(email.trim().toLowerCase());
    admin.setPasswordHash(encoder.encode(password));
    admin.setFullName("Administrator");
    admin.setRole(Role.GRC_ADMIN);
    admin.setEnabled(true);
    users.save(admin);
    log.info("Initial administrator created: {}", admin.getEmail());
  }
}