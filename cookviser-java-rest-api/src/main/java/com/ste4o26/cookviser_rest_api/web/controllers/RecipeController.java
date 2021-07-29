package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.binding_models.RecipeBindingModel;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import com.ste4o26.cookviser_rest_api.domain.response_models.RecipeResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotPresentException;
import com.ste4o26.cookviser_rest_api.exceptions.RecipeNotExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.SearchValueNotProvidedException;
import com.ste4o26.cookviser_rest_api.exceptions.UserNotAuthenticatedException;
import com.ste4o26.cookviser_rest_api.services.interfaces.CloudService;
import com.ste4o26.cookviser_rest_api.services.interfaces.RecipeService;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudService cloudService;

    public RecipeController(RecipeService recipeService, ModelMapper modelMapper, UserService userService, CloudService cloudService) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudService = cloudService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponseModel>> getSearch(@RequestParam(required = false) String searchValue)
            throws SearchValueNotProvidedException {
        List<RecipeServiceModel> recipeServiceModels = this.recipeService.fetchAllContains(searchValue);

        List<RecipeResponseModel> recipeResponseModels = recipeServiceModels.stream()
                .map(recipe -> this.modelMapper.map(recipe, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(recipeResponseModels, OK);
    }

    @GetMapping("/todayBestFour")
    public ResponseEntity<List<RecipeResponseModel>> getTodayBestFour() {
        List<RecipeServiceModel> recipeServiceModels = this.recipeService.fetchBestThreeOrderByRates();

        List<RecipeResponseModel> recipeResponseModels = recipeServiceModels.stream()
                .map(recipeServiceModel -> this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(recipeResponseModels, OK);
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = Arrays.stream(CategoryName.values()).map(Enum::name).collect(Collectors.toList());
        return new ResponseEntity<>(categories, OK);
    }

    //    TODO principal mey not work cause of the jwt token!
    @PostMapping("/create")
    public ResponseEntity<RecipeResponseModel> postAdd(
            @RequestBody RecipeBindingModel recipeBindingModel, Principal principal)
            throws UserNotAuthenticatedException, ImageNotPresentException {
        RecipeServiceModel recipeServiceModel = this.modelMapper.map(recipeBindingModel, RecipeServiceModel.class);

        CategoryName categoryName = CategoryName.valueOf(recipeBindingModel.getCategory().toUpperCase());
        recipeServiceModel.setCategory(categoryName);

        RecipeServiceModel createdRecipe = this.recipeService.persist(recipeServiceModel, principal);
        this.userService.addRecipeToMyRecipes(createdRecipe.getPublisher(), createdRecipe);

        RecipeResponseModel recipeResponseModel = this.modelMapper.map(createdRecipe, RecipeResponseModel.class);

        return new ResponseEntity<>(recipeResponseModel, OK);
    }

    @PostMapping("/upload-recipe-image")
    public ResponseEntity<RecipeResponseModel> uploadRecipeImage(
            @RequestPart("image") MultipartFile multipartFile,
            @RequestParam("recipeId") String recipeId) throws ImageNotPresentException, RecipeNotExistsException {
        if (multipartFile == null || multipartFile.isEmpty()){
            throw new ImageNotPresentException("Image is required!");
        }

        String imageThumbnailUrl;
        try {
            imageThumbnailUrl = this.cloudService.uploadImage(multipartFile);
        } catch (IOException ioe) {
            throw new ImageNotPresentException("Image was not uploaded successfully! Please try again.");
        }

        RecipeServiceModel recipeServiceModel = this.recipeService.fetchById(recipeId);
        recipeServiceModel.setRecipeThumbnail(imageThumbnailUrl);
        RecipeServiceModel updatedRecipe = this.recipeService.update(recipeServiceModel);

        RecipeResponseModel recipeResponseModel = this.modelMapper.map(updatedRecipe, RecipeResponseModel.class);

        return new ResponseEntity<>(recipeResponseModel, OK);
    }

}

