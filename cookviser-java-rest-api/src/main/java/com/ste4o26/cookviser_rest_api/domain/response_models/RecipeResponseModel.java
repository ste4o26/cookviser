package com.ste4o26.cookviser_rest_api.domain.response_models;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeResponseModel {
    private String id;
    private String name;
    private String description;
    private String recipeThumbnail;
    private int portions;
    private int duration;
    private CategoryName category;
    private Set<String> ingredients;
    private String publisherUsername;
    private CuisineResponseModel cuisine;

    @EqualsAndHashCode.Exclude
    private Set<StepResponseModel> steps;

    @EqualsAndHashCode.Exclude
    private Set<UserResponseModel> cookedBy;
    private double overallRating;
}
