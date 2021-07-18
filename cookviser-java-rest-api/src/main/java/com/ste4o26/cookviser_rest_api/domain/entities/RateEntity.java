package com.ste4o26.cookviser_rest_api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "rates")
@Table(name = "rates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RateEntity extends BaseEntity {
    @Column(name = "rate_value")
    private int rateValue;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(targetEntity = RecipeEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private RecipeEntity recipe;
}
