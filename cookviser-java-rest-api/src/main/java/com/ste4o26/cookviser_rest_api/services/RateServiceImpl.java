package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.RateEntity;
import com.ste4o26.cookviser_rest_api.domain.service_models.RateServiceModel;
import com.ste4o26.cookviser_rest_api.repositories.RateRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RateServiceImpl(RateRepository rateRepository, ModelMapper modelMapper) {
        this.rateRepository = rateRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public RateServiceModel rate(RateServiceModel rateServiceModel) {
        RateEntity rateEntity = this.modelMapper.map(rateServiceModel, RateEntity.class);
        RateEntity persited = this.rateRepository.saveAndFlush(rateEntity);

        return this.modelMapper.map(persited, RateServiceModel.class);
    }
}
