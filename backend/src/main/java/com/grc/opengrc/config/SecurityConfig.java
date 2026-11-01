package com.grc.opengrc.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableMethodSecurity // lets us use @PreAuthorize on services and controllers
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // CSRF token in a cookie that Angular reads and sends back as X-XSRF-TOKEN
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

        // deny by default: only these are public
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/login").permitAll()
            .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers("/api/admin/**").hasRole("GRC_ADMIN")
            .anyRequest().authenticated())

        .logout(logout -> logout
            .logoutUrl("/api/auth/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT)))

        // JSON answers instead of an HTML login page
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint((req, res, e) -> writeJson(res, 401, "Unauthorized", "Authentication required"))
            .accessDeniedHandler((req, res, e) -> writeJson(res, 403, "Forbidden", "Access denied")));

    return http.build();
  }

  private static void writeJson(HttpServletResponse res, int status, String error, String message)
      throws IOException {
    res.setStatus(status);
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write("{\"status\":" + status + ",\"error\":\"" + error
        + "\",\"message\":\"" + message + "\"}");
  }

  /** Forces the XSRF-TOKEN cookie to be written on every response. */
  static final class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {
      CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
      if (token != null) {
        token.getToken();
      }
      chain.doFilter(request, response);
    }
  }
}