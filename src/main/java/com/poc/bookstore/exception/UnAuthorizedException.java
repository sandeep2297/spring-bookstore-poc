package com.poc.bookstore.exception;

public class UnAuthorizedException extends RuntimeException {
    final String message;

    public UnAuthorizedException(String message) {
        super(message);
        this.message = message;
    }


}
