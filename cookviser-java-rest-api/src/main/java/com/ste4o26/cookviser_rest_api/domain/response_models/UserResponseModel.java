package com.ste4o26.cookviser_rest_api.domain.response_models;

import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserRoleServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseModel {
    private String username;
    private String email;
    private UserRoleResponseModel role;
    private Set<UserAuthorityResponseModel> authorities;
    private Set<RecipeResponseModel> myRecipes;
    private Set<RecipeResponseModel> myCookedRecipes;
}
