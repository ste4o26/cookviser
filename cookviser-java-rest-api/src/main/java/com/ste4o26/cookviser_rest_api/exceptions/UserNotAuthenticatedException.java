package com.ste4o26.cookviser_rest_api.exceptions;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
