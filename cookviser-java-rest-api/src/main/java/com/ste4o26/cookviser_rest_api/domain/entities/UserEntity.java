package com.ste4o26.cookviser_rest_api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
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

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(targetEntity = UserRoleEntity.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UserRoleEntity role;

    @ManyToMany(targetEntity = UserAuthorityEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<UserAuthorityEntity> authorities;

    //TODO may not be needed!
    @OneToMany(targetEntity = RecipeEntity.class, mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<RecipeEntity> myRecipes;

    @ManyToMany(targetEntity = RecipeEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_cooked_recipes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"))
    private Set<RecipeEntity> myCookedRecipes;

//    @OneToMany(targetEntity = RateEntity.class, mappedBy = "user")
//    private List<RateEntity> rates;
}
