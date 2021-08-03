package com.ste4o26.cookviser_rest_api.domain.service_models;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineServiceModel extends BaseServiceModel {
    private String name;
    private String imageThumbnailUrl;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RecipeServiceModel> recipes;
}
