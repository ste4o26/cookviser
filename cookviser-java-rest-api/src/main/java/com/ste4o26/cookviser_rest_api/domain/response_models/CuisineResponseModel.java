package com.ste4o26.cookviser_rest_api.domain.response_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineResponseModel {
    private String id;
    private String name;
    private String imageThumbnailUrl;
//    private Set<RecipeResponseModel> recipes;
}
