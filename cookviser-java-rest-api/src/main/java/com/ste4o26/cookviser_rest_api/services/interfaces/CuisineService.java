package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.CuisineDontExistsException;

import java.util.List;

public interface CuisineService {
    List<CuisineServiceModel> fetchAll();

    List<CuisineServiceModel> fetchFirstFourMostPopulated();

    CuisineServiceModel fetchByName(String name) throws CuisineDontExistsException;

    CuisineServiceModel persist(CuisineServiceModel cuisineServiceModel);
}
