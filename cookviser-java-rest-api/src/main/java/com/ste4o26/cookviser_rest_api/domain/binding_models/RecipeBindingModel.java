package com.ste4o26.cookviser_rest_api.domain.binding_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeBindingModel {
    private String name;
    private String description;
    private int portions;
    private int duration;
    private String category;
    private CuisineBindingModel cuisine;
    private List<String> ingredients;
    private Set<StepBindingModel> steps;
    private String publisher;
//    private MultipartFile recipeImage;
}
