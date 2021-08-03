package com.ste4o26.cookviser_rest_api.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "cuisines")
@Table(name = "cuisines")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_thumbnail_url", nullable = false)
    private String imageThumbnailUrl;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(targetEntity = RecipeEntity.class, mappedBy = "cuisine", fetch = FetchType.EAGER)
    private Set<RecipeEntity> recipes;
}
