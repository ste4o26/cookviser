package com.ste4o26.cookviser_rest_api.domain.response_models;

import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserRoleServiceModel;
import lombok.*;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseModel {
    private String id;
    private String username;
    private String email;
    private String profileImageUrl;
    private String description;
    private UserRoleResponseModel role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<UserAuthorityResponseModel> authorities;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RecipeResponseModel> myRecipes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RecipeResponseModel> myCookedRecipes;

    private double overallRating;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Set<RateResponseModel> rates;
}
