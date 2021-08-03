package com.ste4o26.cookviser_rest_api.domain.response_models;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineResponseModel {
    private String id;
    private String name;
    private String imageThumbnailUrl;
    
//    TODO ot tova mi gurmi pri suzdavaneto na nova recepta
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Set<RecipeResponseModel> recipes;
}