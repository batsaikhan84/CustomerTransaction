package com.customer.relationship.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
