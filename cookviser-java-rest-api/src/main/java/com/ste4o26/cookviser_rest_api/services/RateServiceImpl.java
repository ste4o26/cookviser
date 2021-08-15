package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.RateEntity;
import com.ste4o26.cookviser_rest_api.domain.service_models.RateServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.repositories.RateRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        String userId = rateServiceModel.getUser().getId();
        String recipeId = rateServiceModel.getRecipe().getId();
        Optional<RateEntity> oldRateOpt = this.rateRepository.findByUserIdAndRecipeId(userId, recipeId);

        RateEntity rateEntity;
        if (oldRateOpt.isEmpty()) {
            rateEntity = this.modelMapper.map(rateServiceModel, RateEntity.class);
        } else {
            rateEntity = oldRateOpt.get();
            rateEntity.setRateValue(rateServiceModel.getRateValue());
        }

        RateEntity result = this.rateRepository.saveAndFlush(rateEntity);
        return this.modelMapper.map(result, RateServiceModel.class);
    }

    @Override
    public double calculateUserOverallRate(UserServiceModel user) {
        int totalRateSum = 0;
        int totalRateCount = 0;
        for (RecipeServiceModel recipe : user.getMyRecipes()) {
            for (RateServiceModel rate : recipe.getRates()) {
                if (rate.getRateValue() == 0) continue;
                totalRateCount++;
                totalRateSum += rate.getRateValue();
            }
        }

        double overallRate = (double) totalRateSum / (totalRateCount);
        if (Double.isNaN(overallRate)) return 0;
        return overallRate;
    }

    @Override
    public double calculateRecipeOverallRate(RecipeServiceModel recipe) {
        int totalRateSum = 0;
        int totalZeroRatesCount = 0;
        for (RateServiceModel rate : recipe.getRates()) {
            if (rate.getRateValue() == 0) {
                totalZeroRatesCount++;
                continue;
            }
            totalRateSum += rate.getRateValue();
        }

        double result = (double) totalRateSum / (recipe.getRates().size() - totalZeroRatesCount);
        if (Double.isNaN(result)) return 0;
        return result;
    }
}
