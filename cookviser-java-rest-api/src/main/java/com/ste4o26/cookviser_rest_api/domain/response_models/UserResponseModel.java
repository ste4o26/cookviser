package com.ste4o26.cookviser_rest_api.domain.response_models;

import lombok.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseModel extends BaseResponseModel{
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
}
