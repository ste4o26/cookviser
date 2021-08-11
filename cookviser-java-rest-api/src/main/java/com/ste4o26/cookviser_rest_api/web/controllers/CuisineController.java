package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.response_models.CuisineResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotPresentException;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotUploadedException;
import com.ste4o26.cookviser_rest_api.services.interfaces.CloudService;
import com.ste4o26.cookviser_rest_api.services.interfaces.CuisineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.IMAGE_NOT_PRESENT;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/cuisine")
public class CuisineController {
    private final CuisineService cuisineService;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;

    @Autowired
    public CuisineController(CuisineService cuisineService, ModelMapper modelMapper, CloudService cloudService) {
        this.cuisineService = cuisineService;
        this.modelMapper = modelMapper;
        this.cloudService = cloudService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CuisineResponseModel>> getAll() {
        List<CuisineServiceModel> allCuisines = this.cuisineService.fetchAll();

        List<CuisineResponseModel> collect = allCuisines.stream()
                .map(cuisineServiceModel -> this.modelMapper.map(cuisineServiceModel, CuisineResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @GetMapping("/first-four-most-populated")
    public ResponseEntity<List<CuisineResponseModel>> getFirstFourMostPopulated() {
        List<CuisineServiceModel> firstFourMostPopulated = this.cuisineService.fetchFirstFourMostPopulated();

        List<CuisineResponseModel> collect = firstFourMostPopulated.stream()
                .map(cuisineServiceModel -> this.modelMapper.map(cuisineServiceModel, CuisineResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CuisineResponseModel> create(
            @RequestPart("cuisineImage") MultipartFile multipartFile,
            @RequestPart("name") String name) throws ImageNotPresentException, ImageNotUploadedException {

        if (multipartFile == null) {
            throw new ImageNotPresentException(IMAGE_NOT_PRESENT);
        }

        String imageUrl = this.cloudService.uploadImage(multipartFile);

        CuisineServiceModel cuisineServiceModel = new CuisineServiceModel(name, imageUrl, new HashSet<>());
        CuisineServiceModel createdCuisine = this.cuisineService.persist(cuisineServiceModel);
        CuisineResponseModel cuisineResponseModel = this.modelMapper.map(createdCuisine, CuisineResponseModel.class);

        return new ResponseEntity<>(cuisineResponseModel, OK);
    }

}
