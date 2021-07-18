package com.ste4o26.cookviser_rest_api.domain.entities;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "roles")
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRoleEntity extends BaseEntity {
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleName role;

//    @ToString.Exclude
//    @ManyToMany(targetEntity = UserAuthorityEntity.class, fetch = FetchType.EAGER)
//    @JoinTable(name = "roles_authorities",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
//    private Set<UserAuthorityEntity> authorities;
}
