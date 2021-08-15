package com.ste4o26.cookviser_rest_api.repositories;

import com.ste4o26.cookviser_rest_api.domain.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, String> {
    Optional<RateEntity> findByUserIdAndRecipeId(String userId, String recipeId);
}
