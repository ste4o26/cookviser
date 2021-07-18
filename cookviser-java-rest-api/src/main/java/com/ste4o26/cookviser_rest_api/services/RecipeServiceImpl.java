package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.RecipeEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.ImageNotPresentException;
import com.ste4o26.cookviser_rest_api.exceptions.RecipeNotExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.SearchValueNotProvidedException;
import com.ste4o26.cookviser_rest_api.exceptions.UserNotAuthenticatedException;
import com.ste4o26.cookviser_rest_api.repositories.RecipeRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.CloudService;
import com.ste4o26.cookviser_rest_api.services.interfaces.RecipeService;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudService cloudService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper, UserService userService, CloudService cloudService) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudService = cloudService;
    }

    @Override
    public RecipeServiceModel persist(RecipeServiceModel recipeServiceModel, MultipartFile multipartFile, Principal principal)
            throws UserNotAuthenticatedException, ImageNotPresentException {
        if (principal.getName() == null || principal.getName().trim().isEmpty()) {
            throw new UserNotAuthenticatedException(FORBIDDEN_MESSAGE);
        }

        UserServiceModel publisher = this.userService.fetchByUsername(principal.getName());
        recipeServiceModel.setPublisher(publisher);
        recipeServiceModel.setCookedBy(new HashSet<>());

        //TODO think about how to set the steps!!!

        String imageThumbnailUrl;
        try {
            imageThumbnailUrl = this.cloudService.uploadImage(multipartFile);
        } catch (IOException ioe) {
            throw new ImageNotPresentException("Image is required!");
        }

        recipeServiceModel.setRecipeThumbnail(imageThumbnailUrl);

        RecipeEntity recipeEntity = this.modelMapper.map(recipeServiceModel, RecipeEntity.class);
        RecipeEntity persisted = this.recipeRepository.saveAndFlush(recipeEntity);

        return this.modelMapper.map(persisted, RecipeServiceModel.class);
    }

    @Override
    public RecipeServiceModel fetchById(String id) throws RecipeNotExistsException {
        RecipeEntity recipeEntity = this.recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotExistsException(String.format(RECIPE_NOT_EXISTS, id)));

        return this.modelMapper.map(recipeEntity, RecipeServiceModel.class);
    }

    //    TODO may need to add orphanal removal to the entity or something like that!
    @Override
    public RecipeServiceModel deleteById(String id) throws RecipeNotExistsException {
        RecipeServiceModel recipeServiceModel = this.fetchById(id);

        this.recipeRepository.deleteById(id);

        return recipeServiceModel;
    }

    @Override
    public RecipeServiceModel addToCooked(String recipeId, Principal principal)
            throws UserNotAuthenticatedException, RecipeNotExistsException {
        if (principal.getName() == null || principal.getName().trim().isEmpty()) {
            throw new UserNotAuthenticatedException(FORBIDDEN_MESSAGE);
        }

        UserServiceModel userServiceModel = this.userService.fetchByUsername(principal.getName());
        RecipeServiceModel recipeServiceModel = this.fetchById(recipeId);

        userServiceModel.getMyCookedRecipes().add(recipeServiceModel);
        this.userService.update(userServiceModel, principal);

        return recipeServiceModel;
    }

    //    TODO more validations!!!
    @Override
    public List<RecipeServiceModel> fetchAllContains(String searchValue) throws SearchValueNotProvidedException {
        if (searchValue == null || searchValue.trim().isEmpty()) {
            throw new SearchValueNotProvidedException(BLANK_SEARCH_VALUE_MESSAGE);
        }

        List<RecipeEntity> allBySearchedValue = this.recipeRepository.findAllByNameContaining(searchValue);
        return RecipeServiceModel.mapFrom(allBySearchedValue, modelMapper);
    }

    @Override
    public List<RecipeServiceModel> fetchAllByCategory(CategoryName category) {
        List<RecipeEntity> allByCategory = this.recipeRepository.findAllByCategory(category);
        return RecipeServiceModel.mapFrom(allByCategory, modelMapper);
    }

    @Override
    public List<RecipeServiceModel> fetchAllSortedByRate() {
        List<RecipeEntity> allOrderedByRates = this.recipeRepository.findAllOrderedByRates();

        return allOrderedByRates.stream()
                .map(recipe -> this.modelMapper.map(recipe, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }


}
