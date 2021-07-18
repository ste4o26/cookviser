package com.ste4o26.cookviser_rest_api.domain.entities;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "recipes")
@Table(name = "recipes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "recipe_thumbnail", nullable = false)
    private String recipeThumbnail;

    @Column(name = "portions", nullable = false)
    private int portions;

    @Column(name = "duration", nullable = false)
    private LocalDateTime duration;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CategoryName category;

    @ElementCollection
    private Set<String> ingredients;

    @OneToMany(targetEntity = StepEntity.class, mappedBy = "recipe")
    private Set<StepEntity> steps;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity publisher;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "myCookedRecipes")
    private Set<UserEntity> cookedBy;

    @OneToMany(targetEntity = RateEntity.class, mappedBy = "recipe")
    private List<RateEntity> rates;

    //TODO Ratings, likes, dislikes!
}