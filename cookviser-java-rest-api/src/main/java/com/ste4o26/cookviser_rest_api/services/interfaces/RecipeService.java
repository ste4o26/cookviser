package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotPresentException;
import com.ste4o26.cookviser_rest_api.exceptions.RecipeNotExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.SearchValueNotProvidedException;
import com.ste4o26.cookviser_rest_api.exceptions.UserNotAuthenticatedException;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface RecipeService {
    RecipeServiceModel persist(RecipeServiceModel recipeServiceModel, Principal principal)
            throws UserNotAuthenticatedException, ImageNotPresentException;

    RecipeServiceModel fetchById(String id) throws RecipeNotExistsException;

    RecipeServiceModel deleteById(String id) throws RecipeNotExistsException;

    RecipeServiceModel addToCooked(String recipeId, Principal principal) throws UserNotAuthenticatedException, RecipeNotExistsException;

    List<RecipeServiceModel> fetchAllContains(String searchValue) throws SearchValueNotProvidedException;

    List<RecipeServiceModel> fetchAllByCategory(CategoryName category);

    List<RecipeServiceModel> fetchAllSortedByRate();

    List<RecipeServiceModel> fetchBestFourOrderByRates();

    RecipeServiceModel update(RecipeServiceModel recipeServiceModel);

    List<RecipeServiceModel> fetchAll();

    List<RecipeServiceModel> fetchNextByCuisine(CuisineServiceModel cuisine, Integer recipesCount);

    List<RecipeServiceModel> fetchNextRecipes(Integer recipesCount);
}
