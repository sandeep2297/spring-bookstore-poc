package com.poc.bookstore.config.security;

import com.poc.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final String[] authWhiteList = {
            "/auth/**",
            "/actuator/**",
            "/h2-console/**"
    };
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new AccessTokenAuthenticationFailureHandler();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return new AuthorizationAccessDeniedHandler();
    }

    private JwtTokenFilter jwtTokenFilter() throws Exception {
        return new JwtTokenFilter(authenticationConfiguration.getAuthenticationManager(),
                new JwtTokenValidator(secretKey, userRepository),
                authenticationFailureHandler(),
                new NegatedRequestMatcher(new OrRequestMatcher(Arrays.stream(authWhiteList)
                        .map(AntPathRequestMatcher::new).collect(Collectors.toList()))));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .cors()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(jwtTokenFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }
}
