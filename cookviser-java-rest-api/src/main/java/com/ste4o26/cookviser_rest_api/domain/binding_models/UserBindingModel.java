package com.ste4o26.cookviser_rest_api.domain.binding_models;

import com.ste4o26.cookviser_rest_api.domain.service_models.UserAuthorityServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserRoleServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBindingModel extends BaseBindingModel {
    private String username;
    private String email;
    private String description;
    private String profileImageUrl;
    private UserRoleServiceModel role;
    private Set<UserAuthorityServiceModel> authorities;
    private Set<RecipeBindingModel> myRecipes;
    private Set<RecipeBindingModel> myCookedRecipes;
}
