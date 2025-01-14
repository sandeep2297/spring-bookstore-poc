package com.poc.bookstore.exception;


public class NotFoundException extends RuntimeException {

    final String message;

    public NotFoundException(String message) {
        super(message + " not found");
        this.message = message + " not found";
    }

}
