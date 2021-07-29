package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.*;

import javax.management.relation.RoleNotFoundException;
import java.security.Principal;

public interface UserService {
    UserServiceModel fetchByUsername(String username);

    UserServiceModel fetchByEmail(String email) throws EmailNotFoundException;

    UserServiceModel register(UserServiceModel userServiceModel)
            throws RoleNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException;

    UserServiceModel update(UserServiceModel userServiceModel, Principal principal) throws UserNotAuthenticatedException;

    UserServiceModel promote(UserServiceModel userServiceModel) throws RoleNotFoundException, PromotionDeniedException;

    UserServiceModel demote(UserServiceModel userServiceModel) throws DemotionDeniedException, RoleNotFoundException;

    UserServiceModel addRecipeToMyRecipes(UserServiceModel publisher, RecipeServiceModel createdRecipe);
}