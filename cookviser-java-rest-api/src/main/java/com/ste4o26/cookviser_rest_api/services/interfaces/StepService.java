package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.service_models.StepServiceModel;

public interface StepService {
    StepServiceModel persist(StepServiceModel stepServiceModel);
}
