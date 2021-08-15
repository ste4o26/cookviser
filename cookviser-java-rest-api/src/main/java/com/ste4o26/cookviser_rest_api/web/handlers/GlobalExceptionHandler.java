package com.ste4o26.cookviser_rest_api.web.handlers;

import com.ste4o26.cookviser_rest_api.domain.HttpResponseModel;
import com.ste4o26.cookviser_rest_api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<HttpResponseModel> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponseModel httpResponseModel = new HttpResponseModel(httpStatus.value(),
                httpStatus.name().toUpperCase(),
                httpStatus.getReasonPhrase().toUpperCase(),
                message);

        return new ResponseEntity<>(httpResponseModel, httpStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponseModel> incorrectCredentialsExceptionHandler(BadCredentialsException e) {
        return this.createHttpResponse(UNAUTHORIZED, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<HttpResponseModel> passwordsDoesNotMatchExceptionHandler(PasswordNotMatchException e) {
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<HttpResponseModel> usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException e) {
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<HttpResponseModel> emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException e) {
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(SearchValueNotProvidedException.class)
    public ResponseEntity<HttpResponseModel> searchValueNotProvidedExceptionHandler(SearchValueNotProvidedException e) {
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<HttpResponseModel> userNotAuthenticatedExceptionHandler(UserNotAuthenticatedException e) {
        return this.createHttpResponse(FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(RecipeNotExistsException.class)
    public ResponseEntity<HttpResponseModel> recipeNotExistsExceptionHandler(RecipeNotExistsException e) {
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponseModel> accessDeniedExceptionHandler(AccessDeniedException e) {
        return this.createHttpResponse(UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ImageNotPresentException.class)
    public ResponseEntity<HttpResponseModel> imageNotPresentExceptionHandler(ImageNotPresentException e) {
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(PromotionDeniedException.class)
    public ResponseEntity<HttpResponseModel> promotionDeniedExceptionHandler(PromotionDeniedException e){
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DemotionDeniedException.class)
    public ResponseEntity<HttpResponseModel> demotionDeniedExceptionHandler(DemotionDeniedException e){
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(CuisineDontExistsException.class)
    public ResponseEntity<HttpResponseModel> cuisineDontExistsExceptionHandler(CuisineDontExistsException e){
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponseModel> imageNotUploadedExceptionHandler(ImageNotUploadedException e){
        return this.createHttpResponse(BAD_REQUEST, e.getMessage());
    }
//    TODO make an exception handler for TokenExpiredException!!!
}
