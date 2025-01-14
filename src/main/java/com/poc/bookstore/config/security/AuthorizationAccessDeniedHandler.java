package com.poc.bookstore.config.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.Instant;

public class AuthorizationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(createErrorBody(request, accessDeniedException));
    }

    private String createErrorBody(HttpServletRequest request, AccessDeniedException accessDeniedException) {
        JsonObject exceptionMessage = new JsonObject();
        exceptionMessage.addProperty("status", HttpStatus.FORBIDDEN.value());
        exceptionMessage.addProperty("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        exceptionMessage.addProperty("timeStamp", Instant.now().toString());
        exceptionMessage.addProperty("message", accessDeniedException.getMessage());
        return new Gson().toJson(exceptionMessage);
    }

}
