package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.response_models.RecipeResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.SearchValueNotProvidedException;
import com.ste4o26.cookviser_rest_api.services.interfaces.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final ModelMapper modelMapper;

    public RecipeController(RecipeService recipeService, ModelMapper modelMapper) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponseModel>> search(@RequestParam(required = false) String searchValue)
            throws SearchValueNotProvidedException {
        List<RecipeServiceModel> recipeServiceModels = this.recipeService.fetchAllContains(searchValue);

        List<RecipeResponseModel> recipeResponseModels = recipeServiceModels.stream()
                .map(recipe -> this.modelMapper.map(recipe, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(recipeResponseModels, HttpStatus.OK);
    }
}

