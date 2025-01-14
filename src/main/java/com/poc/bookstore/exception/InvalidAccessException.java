package com.poc.bookstore.exception;

public class InvalidAccessException extends RuntimeException {

    final String message;

    public InvalidAccessException(String message) {
        super(message);
        this.message = message;
    }

}
