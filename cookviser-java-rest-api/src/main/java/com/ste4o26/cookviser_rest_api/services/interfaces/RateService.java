package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.service_models.RateServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;

public interface RateService {
    RateServiceModel rate(RateServiceModel rateServiceModel);

    double calculateUserOverallRate(UserServiceModel user);

    double calculateRecipeOverallRate(RecipeServiceModel recipe);
}
