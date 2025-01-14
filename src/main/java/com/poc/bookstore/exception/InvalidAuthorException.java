package com.poc.bookstore.exception;

public class InvalidAuthorException extends RuntimeException {

    final String message;

    public InvalidAuthorException(String message) {
        super(message);
        this.message = message;
    }

}
