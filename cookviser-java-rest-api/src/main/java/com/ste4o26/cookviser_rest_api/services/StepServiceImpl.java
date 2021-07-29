package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.StepEntity;
import com.ste4o26.cookviser_rest_api.domain.service_models.StepServiceModel;
import com.ste4o26.cookviser_rest_api.repositories.StepRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.StepService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepServiceImpl implements StepService {
    private final StepRepository stepRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StepServiceImpl(StepRepository stepRepository, ModelMapper modelMapper) {
        this.stepRepository = stepRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StepServiceModel persist(StepServiceModel stepServiceModel) {
        StepEntity stepEntity = this.modelMapper.map(stepServiceModel, StepEntity.class);
        StepEntity created = this.stepRepository.saveAndFlush(stepEntity);

        return this.modelMapper.map(created, StepServiceModel.class);
    }
}
