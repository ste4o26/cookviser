package com.ste4o26.cookviser_rest_api.domain.service_models;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRoleServiceModel extends BaseServiceModel {
    private RoleName role;
}
