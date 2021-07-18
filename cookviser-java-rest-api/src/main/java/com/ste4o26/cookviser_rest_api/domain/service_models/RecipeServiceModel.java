package com.ste4o26.cookviser_rest_api.domain.service_models;

import com.ste4o26.cookviser_rest_api.domain.entities.RateEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.StepEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.UserEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeServiceModel extends BaseServiceModel {
    private String name;
    private String description;
    private String recipeThumbnail;
    private int portions;
    private LocalDateTime duration;
    private CategoryName category;
    private Set<String> ingredients;
    private Set<StepServiceModel> steps;
    private UserServiceModel publisher;
    private Set<UserServiceModel> cookedBy;
    private List<RateServiceModel> rates;

    //    TODO validation constraints!

    public static <T> List<RecipeServiceModel> mapFrom(List<T> recipes, ModelMapper modelMapper) {
        return recipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }
}
