package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.CuisineEntity;
import com.ste4o26.cookviser_rest_api.domain.service_models.CuisineServiceModel;
import com.ste4o26.cookviser_rest_api.repositories.CuisineRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.CuisineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuisineServiceImpl implements CuisineService {
    private final CuisineRepository cuisineRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CuisineServiceImpl(CuisineRepository cuisineRepository, ModelMapper modelMapper) {
        this.cuisineRepository = cuisineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CuisineServiceModel> fetchAll() {
        List<CuisineEntity> allCuisines = this.cuisineRepository.findAll();

        return allCuisines.stream()
                .map(cuisineEntity -> this.modelMapper.map(cuisineEntity, CuisineServiceModel.class))
                .collect(Collectors.toList());
    }
}
