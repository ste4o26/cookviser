package com.ste4o26.cookviser_rest_api.web.controllers;

import com.ste4o26.cookviser_rest_api.domain.binding_models.RateBindingModel;
import com.ste4o26.cookviser_rest_api.domain.binding_models.RecipeBindingModel;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import com.ste4o26.cookviser_rest_api.domain.response_models.RateResponseModel;
import com.ste4o26.cookviser_rest_api.domain.response_models.RecipeResponseModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RateServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.*;
import com.ste4o26.cookviser_rest_api.services.interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.*;
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
    private final CuisineService cuisineService;

    public RecipeController(RecipeService recipeService, ModelMapper modelMapper, UserService userService, CloudService cloudService, RateService rateService, CuisineService cuisineService) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudService = cloudService;
        this.rateService = rateService;
        this.cuisineService = cuisineService;
    }

    @GetMapping("/next-recipes")
    public ResponseEntity<List<RecipeResponseModel>> getNextRecipes(@RequestParam("recipesCount") Integer recipesCount) {
        List<RecipeServiceModel> nextPageRecipes = this.recipeService.fetchNextRecipes(recipesCount);

        List<RecipeResponseModel> collect = nextPageRecipes.stream()
                .map(recipeServiceModel -> this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @GetMapping("/next-by-cuisine")
    public ResponseEntity<List<RecipeResponseModel>> getAllByCuisine(
            @RequestParam("cuisineName") String cuisineName, @RequestParam("recipesCount") Integer recipesCount)
            throws CuisineDontExistsException {
        CuisineServiceModel cuisineServiceModel = this.cuisineService.fetchByName(cuisineName);
        List<RecipeServiceModel> recipesByCuisine = this.recipeService.fetchNextByCuisine(cuisineServiceModel, recipesCount);

        List<RecipeResponseModel> collect = recipesByCuisine.stream()
                .map(recipeServiceModel -> this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
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
    public ResponseEntity<List<RecipeResponseModel>> getSearch(@RequestParam("searchValue") String searchValue)
            throws SearchValueNotProvidedException {
        List<RecipeServiceModel> recipeServiceModels = this.recipeService.fetchAllContains(searchValue);

        List<RecipeResponseModel> collect = recipeServiceModels.stream()
                .map(recipe -> this.modelMapper.map(recipe, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
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

        List<RecipeResponseModel> collect = recipeServiceModels.stream()
                .map(recipeServiceModel -> this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, OK);
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> collect = Arrays.stream(CategoryName.values()).map(Enum::name).collect(Collectors.toList());
        return new ResponseEntity<>(collect, OK);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/create")
    public ResponseEntity<RecipeResponseModel> postCreate(
            @RequestBody RecipeBindingModel recipeBindingModel, Principal principal)
            throws UserNotAuthenticatedException, ImageNotPresentException {
        RecipeServiceModel recipeServiceModel = this.modelMapper.map(recipeBindingModel, RecipeServiceModel.class);

        CategoryName categoryName = CategoryName.valueOf(recipeBindingModel.getCategory().toUpperCase());
        recipeServiceModel.setCategory(categoryName);

        RecipeServiceModel createdRecipe = this.recipeService.persist(recipeServiceModel, principal);
        this.userService.addRecipeToMyRecipes(createdRecipe.getPublisher(), createdRecipe);

        RecipeResponseModel recipeResponseModel = this.modelMapper.map(createdRecipe, RecipeResponseModel.class);
        return new ResponseEntity<>(recipeResponseModel, CREATED);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/upload-recipe-image")
    public ResponseEntity<RecipeResponseModel> postUploadRecipeImage(
            @RequestPart("image") MultipartFile multipartFile,
            @RequestParam("recipeId") String recipeId) throws RecipeNotExistsException, ImageNotUploadedException, ImageNotPresentException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ImageNotPresentException(IMAGE_NOT_PRESENT);
        }

        String imageThumbnailUrl = this.cloudService.uploadImage(multipartFile);

        RecipeServiceModel recipeServiceModel = this.recipeService.fetchById(recipeId);
        recipeServiceModel.setRecipeThumbnail(imageThumbnailUrl);
        RecipeServiceModel updatedRecipe = this.recipeService.update(recipeServiceModel);

        RecipeResponseModel recipeResponseModel = this.modelMapper.map(updatedRecipe, RecipeResponseModel.class);
        return new ResponseEntity<>(recipeResponseModel, CREATED);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/rate")
    public ResponseEntity<RateResponseModel> postRate(@RequestBody RateBindingModel rateBindingModel) {
        RateServiceModel rateServiceModel = this.modelMapper.map(rateBindingModel, RateServiceModel.class);
        RateServiceModel rate = this.rateService.rate(rateServiceModel);

        RateResponseModel rateResponseModel = this.modelMapper.map(rate, RateResponseModel.class);
        return new ResponseEntity<>(rateResponseModel, CREATED);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/delete")
    public ResponseEntity<RecipeResponseModel> delete(@RequestParam("recipeId") String recipeId) throws RecipeNotExistsException {
        RecipeServiceModel recipeServiceModel = this.recipeService.deleteById(recipeId);
        RecipeResponseModel recipeResponseModel = this.modelMapper.map(recipeServiceModel, RecipeResponseModel.class);
        return new ResponseEntity<>(recipeResponseModel, OK);
    }
}

