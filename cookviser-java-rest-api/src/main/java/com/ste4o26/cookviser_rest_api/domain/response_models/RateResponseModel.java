package com.ste4o26.cookviser_rest_api.domain.response_models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RateResponseModel {
    private int rateValue;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserResponseModel user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RecipeResponseModel recipe;
}
