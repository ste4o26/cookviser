package com.ste4o26.cookviser_rest_api.repositories;

import com.ste4o26.cookviser_rest_api.domain.entities.CuisineEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.RecipeEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, String> {
    List<RecipeEntity> findAllByNameContaining(String searchValue);

    List<RecipeEntity> findAllByCuisine(CuisineEntity cuisine, Pageable pageable);
}
