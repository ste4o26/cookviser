package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserAuthorityServiceModel;

import java.util.Set;

public interface AuthorityService {
    Set<UserAuthorityServiceModel> fetchAll();

    UserAuthorityServiceModel fetchByName(AuthorityName name);
}
