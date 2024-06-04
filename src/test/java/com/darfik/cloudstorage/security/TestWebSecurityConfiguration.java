package com.darfik.cloudstorage.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@TestConfiguration
public class TestWebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/signup/**", "/login/**", "/**")
                            .permitAll();
                    registry.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                            .permitAll();
                    registry.requestMatchers("/webjars/**")
                            .permitAll();
                    registry.requestMatchers("/signup-form.js", "/signup-form.css",
                                    "/password-validator.js", "/drop-zone.js")
                            .permitAll();
                    registry.anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/login").usernameParameter("email")
                                .defaultSuccessUrl("/").permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher(
                                "/logout")));

        return httpSecurity.build();
    }

}
