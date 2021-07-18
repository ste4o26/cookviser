package com.ste4o26.cookviser_rest_api.domain.service_models;

import com.ste4o26.cookviser_rest_api.domain.entities.UserAuthorityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserServiceModel extends BaseServiceModel {
    private String username;
    private String email;
    private String password;
    private UserRoleServiceModel role;
    private Set<UserAuthorityServiceModel> authorities;
    private Set<RecipeServiceModel> myRecipes;
    private Set<RecipeServiceModel> myCookedRecipes;

    public <T> T mapTo(Class<T> clazz, ModelMapper modelMapper) {
        return modelMapper.map(this, clazz);
    }

    //    TODO validation constraints!

}
