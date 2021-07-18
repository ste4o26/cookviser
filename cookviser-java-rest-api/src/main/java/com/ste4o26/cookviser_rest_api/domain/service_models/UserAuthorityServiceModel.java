package com.ste4o26.cookviser_rest_api.domain.service_models;

import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAuthorityServiceModel extends BaseServiceModel{
    private AuthorityName authority;
}
