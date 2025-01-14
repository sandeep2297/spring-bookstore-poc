package com.poc.bookstore.config.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.time.Instant;

public class AccessTokenAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(createErrorBody(request, exception));
    }

    private String createErrorBody(HttpServletRequest request, AuthenticationException exception) {
        JsonObject exceptionMessage = new JsonObject();
        exceptionMessage.addProperty("status", HttpStatus.UNAUTHORIZED.value());
        exceptionMessage.addProperty("reason", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        exceptionMessage.addProperty("timeStamp", Instant.now().toString());
        exceptionMessage.addProperty("message", exception.getMessage());
        return new Gson().toJson(exceptionMessage);
    }

}
