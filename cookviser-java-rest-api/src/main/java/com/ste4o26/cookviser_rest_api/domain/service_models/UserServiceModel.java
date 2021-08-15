package com.ste4o26.cookviser_rest_api.domain.service_models;

import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserServiceModel extends BaseServiceModel {
    private String username;
    private String email;
    private String profileImageUrl;
    private String description;
    private String password;
    private UserRoleServiceModel role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<UserAuthorityServiceModel> authorities;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RecipeServiceModel> myRecipes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RecipeServiceModel> myCookedRecipes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RateServiceModel> rates;

    private double overallRating;

    public <T> T mapTo(Class<T> clazz, ModelMapper modelMapper) {
        return modelMapper.map(this, clazz);
    }
}
