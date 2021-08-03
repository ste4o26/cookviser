package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.response_models.CuisineResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;
import com.ste4o26.cookviser_rest_api.services.interfaces.CuisineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/cuisine")
public class CuisineController {
    private final CuisineService cuisineService;
    private final ModelMapper modelMapper;

    @Autowired
    public CuisineController(CuisineService cuisineService, ModelMapper modelMapper) {
        this.cuisineService = cuisineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CuisineResponseModel>> getAll() {
        List<CuisineServiceModel> allCuisines = this.cuisineService.fetchAll();

        List<CuisineResponseModel> collect = allCuisines.stream()
                .map(cuisineServiceModel -> this.modelMapper.map(cuisineServiceModel, CuisineResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @GetMapping("/first-three-most-populated")
    public ResponseEntity<List<CuisineResponseModel>> getFirstThreeMostPopulated() {
        List<CuisineServiceModel> firstThreeMostPopulated = this.cuisineService.fetchFirstThreeMostPopulated();

        List<CuisineResponseModel> collect = firstThreeMostPopulated.stream()
                .map(cuisineServiceModel -> this.modelMapper.map(cuisineServiceModel, CuisineResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }
}
