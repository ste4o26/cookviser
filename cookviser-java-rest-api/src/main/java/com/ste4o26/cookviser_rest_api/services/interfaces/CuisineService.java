package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;

import java.util.List;

public interface CuisineService {
    List<CuisineServiceModel> fetchAll();

    List<CuisineServiceModel> fetchFirstThreeMostPopulated();
}
