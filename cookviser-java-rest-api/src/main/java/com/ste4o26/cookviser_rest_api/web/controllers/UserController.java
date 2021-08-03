package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.response_models.UserResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.services.interfaces.RateService;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RateService rateService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, RateService rateService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.rateService = rateService;
    }

    @GetMapping("/best-three")
    public ResponseEntity<List<UserResponseModel>> getBestThree() {
        List<UserServiceModel> bestThreeChefs = this.userService.fetchBestThreeChefs();

        List<UserResponseModel> collect = bestThreeChefs.stream()
                .map(userServiceModel -> this.modelMapper.map(userServiceModel, UserResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @GetMapping("/by-username")
    public ResponseEntity<UserResponseModel> getByUsername(@RequestParam("username") String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Username is required!");
        }

        UserServiceModel userServiceModel = this.userService.fetchByUsername(username);

        UserResponseModel userResponseModel = this.modelMapper.map(userServiceModel, UserResponseModel.class);

        return new ResponseEntity<>(userResponseModel, OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<UserResponseModel>> getAll() {
        List<UserServiceModel> allUsers = this.userService.fetchAll();

        List<UserResponseModel> collect = allUsers.stream()
                .map(userServiceModel -> this.modelMapper.map(userServiceModel, UserResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

}
