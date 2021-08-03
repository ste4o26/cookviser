package com.ste4o26.cookviser_rest_api.domain.binding_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RateBindingModel {
    private int rateValue;
    private UserBindingModel user;
    private RecipeBindingModel recipe;
}
