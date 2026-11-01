package com.grc.opengrc.security;

import com.grc.opengrc.entity.user.User;
import com.grc.opengrc.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

  private final UserRepository users;

  public AppUserDetailsService(UserRepository users) {
    this.users = users;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User u = users.findByEmailIgnoreCase(email)
        .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

    return org.springframework.security.core.userdetails.User
        .withUsername(u.getEmail())
        .password(u.getPasswordHash())
        .authorities("ROLE_" + u.getRole().name())
        .disabled(!u.isEnabled())
        .build();
  }
}