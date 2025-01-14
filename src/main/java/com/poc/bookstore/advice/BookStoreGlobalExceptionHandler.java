package com.poc.bookstore.advice;

import com.poc.bookstore.dto.Response;
import com.poc.bookstore.exception.InvalidAccessException;
import com.poc.bookstore.exception.InvalidAuthorException;
import com.poc.bookstore.exception.InvalidTokenException;
import com.poc.bookstore.exception.NotFoundException;
import com.poc.bookstore.exception.UnAuthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class BookStoreGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<Response> handleGenericExceptions(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getContextPath(), ex.getMessage(), new Date()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Response> handleNotFoundExceptions(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), request.getContextPath(), ex.getMessage(), new Date()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidAccessException.class})
    public ResponseEntity<Response> handleInvalidAccessExceptions(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), request.getContextPath(), ex.getMessage(), new Date()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {DuplicateKeyException.class, InvalidAuthorException.class})
    public ResponseEntity<Response> handleDuplicateRequestExceptions(
            RuntimeException ex, WebRequest request) {

        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), request.getContextPath(), ex.getMessage(), new Date()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnAuthorizedException.class, InvalidTokenException.class})
    public ResponseEntity<Response> handleUnAuthorizedRequestExceptions(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), request.getContextPath(), ex.getMessage(), new Date()),
                HttpStatus.UNAUTHORIZED);
    }

}
