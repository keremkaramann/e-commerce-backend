package com.ecommerce.backend.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TestConfig {
   @Bean
    public UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }
}
