package com.ste4o26.cookviser_rest_api.domain.service_models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RateServiceModel extends BaseServiceModel {
    private int rateValue;
    private UserServiceModel user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RecipeServiceModel recipe;
}
