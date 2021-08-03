package com.ste4o26.cookviser_rest_api.domain.service_models;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import lombok.*;
import org.modelmapper.ModelMapper;

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
    private int duration;
    private CategoryName category;
    private CuisineServiceModel cuisine;
    private Set<String> ingredients;
    private Set<StepServiceModel> steps;
    private Set<UserServiceModel> cookedBy;
    private Set<RateServiceModel> rates;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserServiceModel publisher;

    private double overallRating;

    //    TODO validation constraints!

    public static <T> List<RecipeServiceModel> mapFrom(List<T> recipes, ModelMapper modelMapper) {
        return recipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }
}
