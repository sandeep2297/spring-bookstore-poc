package com.poc.bookstore.config.security;

import com.poc.bookstore.util.HttpRequestHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

import static com.poc.bookstore.util.JwtUtil.extractAuthorizationHeaderAsString;

public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenValidator jwtTokenValidator;

    public JwtTokenFilter(AuthenticationManager authenticationManager,
                          JwtTokenValidator jwtTokenValidator,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          RequestMatcher publicPath) {
        super(publicPath);
        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(authenticationFailureHandler);
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authorizationHeader = extractAuthorizationHeaderAsString(request);
        AccessToken accessToken = jwtTokenValidator.validateAuthorizationHeader(authorizationHeader);
        return this.getAuthenticationManager().authenticate(new JwtAuthentication(accessToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        HttpRequestHandler requestHandler = new HttpRequestHandler(request);
        requestHandler.addHeader("userId", authResult.getName());
        filterChain.doFilter(requestHandler, response);
    }


}
