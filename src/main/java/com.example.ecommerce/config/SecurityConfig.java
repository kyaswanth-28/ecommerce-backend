package com.example.ecommerce.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService users(PasswordEncoder e,
 @Value("${app.customer.username:customer}") String cu,@Value("${app.customer.password:customer123}") String cp,
 @Value("${app.admin.username:admin}") String au,@Value("${app.admin.password:admin123}") String ap){
  return new InMemoryUserDetailsManager(
   User.withUsername(cu).password(e.encode(cp)).roles("CUSTOMER").build(),
   User.withUsername(au).password(e.encode(ap)).roles("ADMIN").build());
 }
 @Bean SecurityFilterChain filter(HttpSecurity http)throws Exception{
  return http.csrf(c->c.disable()).authorizeHttpRequests(a->a
   .requestMatchers("/api/products","/api/products/**").permitAll().anyRequest().authenticated())
   .httpBasic(Customizer.withDefaults()).build();
 }
}
