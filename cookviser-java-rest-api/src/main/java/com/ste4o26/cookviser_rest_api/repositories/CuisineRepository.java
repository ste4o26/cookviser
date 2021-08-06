package com.ste4o26.cookviser_rest_api.repositories;

import com.ste4o26.cookviser_rest_api.domain.entities.CuisineEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CuisineRepository extends JpaRepository<CuisineEntity, String> {

    @Query("SELECT c FROM cuisines AS c " +
            "LEFT JOIN recipes AS r " +
            "ON c.id = r.cuisine.id " +
            "GROUP BY c.name " +
            "ORDER BY c.recipes.size DESC")
    List<CuisineEntity> findFirstThreeMostPopulated(Pageable pageable);

    Optional<CuisineEntity> findByNameIgnoreCase(String name);
}
