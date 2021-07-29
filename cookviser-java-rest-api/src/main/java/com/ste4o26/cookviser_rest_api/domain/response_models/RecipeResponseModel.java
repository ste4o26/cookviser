package com.ste4o26.cookviser_rest_api.domain.response_models;

import com.ste4o26.cookviser_rest_api.domain.entities.StepEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.UserEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeResponseModel {
    private String id;
    private String name;
    private String description;
    private String recipeThumbnail;
    private int portions;
    private int duration;
    private CategoryName category;
    private List<String> ingredients;
    private Set<StepResponseModel> step;
    private UserEntity publisher;
    private Set<UserEntity> cookedBy;
}
