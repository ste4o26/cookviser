package com.ste4o26.cookviser_rest_api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity extends BaseEntity {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(targetEntity = UserRoleEntity.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UserRoleEntity role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(targetEntity = UserAuthorityEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<UserAuthorityEntity> authorities;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(targetEntity = RecipeEntity.class, mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<RecipeEntity> myRecipes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(targetEntity = RecipeEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_cooked_recipes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"))
    private Set<RecipeEntity> myCookedRecipes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(targetEntity = RateEntity.class, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<RateEntity> rates;
}
