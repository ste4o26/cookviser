package com.ste4o26.cookviser_rest_api.domain.binding_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeBindingModel extends BaseBindingModel {
    private String name;
    private String description;
    private int portions;
    private int duration;
    private String category;
    private CuisineBindingModel cuisine;
    private List<String> ingredients;
    private Set<StepBindingModel> steps;
    private String publisher;
}
