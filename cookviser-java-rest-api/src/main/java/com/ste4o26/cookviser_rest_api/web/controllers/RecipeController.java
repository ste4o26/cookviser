package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.binding_models.RateBindingModel;
import com.ste4o26.cookviser_rest_api.domain.binding_models.RecipeBindingModel;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import com.ste4o26.cookviser_rest_api.domain.response_models.RateResponseModel;
import com.ste4o26.cookviser_rest_api.domain.response_models.RecipeResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RateServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotPresentException;
import com.ste4o26.cookviser_rest_api.exceptions.RecipeNotExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.SearchValueNotProvidedException;
import com.ste4o26.cookviser_rest_api.exceptions.UserNotAuthenticatedException;
import com.ste4o26.cookviser_rest_api.init.ErrorMessages;
import com.ste4o26.cookviser_rest_api.services.interfaces.CloudService;
import com.ste4o26.cookviser_rest_api.services.interfaces.RateService;
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

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.IMAGE_NOT_UPLOADED;
import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.RECIPE_NOT_EXISTS;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudService cloudService;
    private final RateService rateService;

    public RecipeController(RecipeService recipeService, ModelMapper modelMapper, UserService userService, CloudService cloudService, RateService rateService) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudService = cloudService;
        this.rateService = rateService;
    }

    @GetMapping("/details")
    public ResponseEntity<RecipeResponseModel> getById(@RequestParam("recipeId") String recipeId) throws RecipeNotExistsException {
        if (recipeId == null || recipeId.trim().isEmpty()) {
            throw new RecipeNotExistsException(RECIPE_NOT_EXISTS);
        }

        RecipeServiceModel recipeServiceModel = this.recipeService.fetchById(recipeId);
        RecipeResponseModel recipeResponseModel = this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class);

        double overallRate = this.rateService.calculateRecipeOverallRate(recipeServiceModel);
        recipeResponseModel.setOverallRating(overallRate);

        return new ResponseEntity<>(recipeResponseModel, OK);
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

    @GetMapping("/best-four")
    public ResponseEntity<List<RecipeResponseModel>> getBestFour() {
        List<RecipeServiceModel> bestFourRecipes = this.recipeService.fetchBestFourOrderByRates();

        List<RecipeResponseModel> collect = bestFourRecipes.stream()
                .map(recipeServiceModel -> this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecipeResponseModel>> getAll() {
        List<RecipeServiceModel> recipeServiceModels = this.recipeService.fetchAll();

        List<RecipeResponseModel> allRecipes = recipeServiceModels.stream()
                .map(recipeServiceModel -> this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(allRecipes, OK);
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = Arrays.stream(CategoryName.values()).map(Enum::name).collect(Collectors.toList());
        return new ResponseEntity<>(categories, OK);
    }

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
    public ResponseEntity<RecipeResponseModel> postUploadRecipeImage(
            @RequestPart("image") MultipartFile multipartFile,
            @RequestParam("recipeId") String recipeId) throws ImageNotPresentException, RecipeNotExistsException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ImageNotPresentException("Image is required!");
        }

        String imageThumbnailUrl;
        try {
            imageThumbnailUrl = this.cloudService.uploadImage(multipartFile);
        } catch (IOException ioe) {
            throw new ImageNotPresentException(IMAGE_NOT_UPLOADED);
        }

        RecipeServiceModel recipeServiceModel = this.recipeService.fetchById(recipeId);
        recipeServiceModel.setRecipeThumbnail(imageThumbnailUrl);
        RecipeServiceModel updatedRecipe = this.recipeService.update(recipeServiceModel);

        RecipeResponseModel recipeResponseModel = this.modelMapper.map(updatedRecipe, RecipeResponseModel.class);

        return new ResponseEntity<>(recipeResponseModel, OK);
    }

    @PostMapping("/rate")
    public ResponseEntity<RateResponseModel> postRate(@RequestBody RateBindingModel rateBindingModel) {
        RateServiceModel rateServiceModel = this.modelMapper.map(rateBindingModel, RateServiceModel.class);

        RateServiceModel rate = this.rateService.rate(rateServiceModel);

        RateResponseModel rateResponseModel = this.modelMapper.map(rate, RateResponseModel.class);
        return new ResponseEntity<>(rateResponseModel, OK);
    }

}

