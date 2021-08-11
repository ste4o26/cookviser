package com.ste4o26.cookviser_rest_api.domain.entities;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.CategoryName;
import lombok.*;
import javax.persistence.*;
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

    @Column(name = "recipe_thumbnail")
    private String recipeThumbnail;

    @Column(name = "portions", nullable = false)
    private int portions;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CategoryName category;

    @ManyToOne(targetEntity = CuisineEntity.class)
    @JoinColumn(name = "cuisine_id", referencedColumnName = "id")
    private CuisineEntity cuisine;

    @ElementCollection
    private Set<String> ingredients;

    @OneToMany(targetEntity = StepEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Set<StepEntity> steps;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity publisher;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "myCookedRecipes", fetch = FetchType.EAGER)
    private Set<UserEntity> cookedBy;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(targetEntity = RateEntity.class, mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RateEntity> rates;
}