package com.ste4o26.cookviser_rest_api.domain.entities;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "roles")
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRoleEntity extends BaseEntity {
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleName role;
}
