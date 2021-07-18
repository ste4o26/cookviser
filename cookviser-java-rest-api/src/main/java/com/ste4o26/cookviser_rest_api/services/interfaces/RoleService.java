package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserRoleServiceModel;

import javax.management.relation.RoleNotFoundException;

public interface RoleService {
    UserRoleServiceModel fetchByName(RoleName name) throws RoleNotFoundException;
}
