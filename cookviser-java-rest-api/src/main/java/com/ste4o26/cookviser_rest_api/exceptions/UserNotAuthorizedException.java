package com.ste4o26.cookviser_rest_api.exceptions;

public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
