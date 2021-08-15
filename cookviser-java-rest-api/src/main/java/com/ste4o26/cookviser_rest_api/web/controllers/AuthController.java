package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.binding_models.UserLoginBindingModel;
import com.ste4o26.cookviser_rest_api.domain.binding_models.UserRegisterBindingModel;
import com.ste4o26.cookviser_rest_api.domain.response_models.UserResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.EmailAlreadyExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.PasswordNotMatchException;
import com.ste4o26.cookviser_rest_api.exceptions.UsernameAlreadyExistsException;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import com.ste4o26.cookviser_rest_api.utils.JWTUtil;
import com.ste4o26.cookviser_rest_api.web.handlers.GlobalExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.PASSWORDS_NOT_MATCH;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/auth")
public class AuthController extends GlobalExceptionHandler {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    private HttpHeaders getJWTHeaders(UserServiceModel actualUser) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String token = this.jwtUtil.generateToken(actualUser);
        httpHeaders.add(JWTUtil.JWT_HEADER, token);

        return httpHeaders;
    }

    private void authenticate(UserLoginBindingModel userLoginBindingModel) throws BadCredentialsException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());

        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseModel> register(@RequestBody UserRegisterBindingModel userRegisterBindingModel)
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RoleNotFoundException, PasswordNotMatchException {

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            throw new PasswordNotMatchException(PASSWORDS_NOT_MATCH);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        UserServiceModel registeredUser = this.userService.register(userServiceModel);

        UserResponseModel responseBody = this.modelMapper.map(registeredUser, UserResponseModel.class);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseModel> login(@RequestBody UserLoginBindingModel userLoginBindingModel) {
        this.authenticate(userLoginBindingModel);

        UserServiceModel userServiceModel = this.modelMapper.map(userLoginBindingModel, UserServiceModel.class);
        UserServiceModel actualUser = this.userService.fetchByUsername(userServiceModel.getUsername());

        HttpHeaders JWTHeader = getJWTHeaders(actualUser);
        UserResponseModel responseBody = this.modelMapper.map(actualUser, UserResponseModel.class);

        return new ResponseEntity<>(responseBody, JWTHeader, HttpStatus.OK);
    }
}
