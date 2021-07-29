package com.ste4o26.cookviser_rest_api.domain.service_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineServiceModel extends BaseServiceModel {
    private String name;
    private String imageThumbnailUrl;
//    private Set<RecipeServiceModel> recipes;
}
