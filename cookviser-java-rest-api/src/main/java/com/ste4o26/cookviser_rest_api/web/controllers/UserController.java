package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.binding_models.UserBindingModel;
import com.ste4o26.cookviser_rest_api.domain.response_models.UserResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotUploadedException;
import com.ste4o26.cookviser_rest_api.init.ErrorMessages;
import com.ste4o26.cookviser_rest_api.services.interfaces.CloudService;
import com.ste4o26.cookviser_rest_api.services.interfaces.RateService;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
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
    private final CloudService cloudService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, RateService rateService, CloudService cloudService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.rateService = rateService;
        this.cloudService = cloudService;
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

    @PostMapping("/update-profile-image")
    public ResponseEntity<UserResponseModel> postUpdateUserProfileImage(
            @RequestPart("profileImage") MultipartFile profileImage,
            @RequestParam("username") String username) throws ImageNotUploadedException {
        int b = 5;

        String imageUrl;
        try {
            imageUrl = this.cloudService.uploadImage(profileImage);
        } catch (IOException e) {
            throw new ImageNotUploadedException(ErrorMessages.IMAGE_NOT_UPLOADED);
        }

        UserServiceModel userServiceModel = this.userService.fetchByUsername(username);
        userServiceModel.setProfileImageUrl(imageUrl);

        UserServiceModel updatedUser = this.userService.update(userServiceModel);
        UserResponseModel responseModel = this.modelMapper.map(updatedUser, UserResponseModel.class);

        return new ResponseEntity<>(responseModel, OK);
    }

    @PostMapping("/update-profile")
    public ResponseEntity<UserResponseModel> postUpdateUserProfile(@RequestBody UserBindingModel userBindingModel, Principal principal) {
        UserServiceModel userServiceModel = this.userService.fetchByUsername(principal.getName());
        userServiceModel.setUsername(userBindingModel.getUsername());
        userServiceModel.setEmail(userBindingModel.getEmail());
        userServiceModel.setDescription(userBindingModel.getDescription());

        UserServiceModel updatedUser = this.userService.update(userServiceModel);
        UserResponseModel responseModel = this.modelMapper.map(updatedUser, UserResponseModel.class);

        return new ResponseEntity<>(responseModel, OK);
    }
}
