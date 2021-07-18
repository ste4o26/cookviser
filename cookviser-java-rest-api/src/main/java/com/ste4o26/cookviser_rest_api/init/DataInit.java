package com.ste4o26.cookviser_rest_api.init;

import com.ste4o26.cookviser_rest_api.domain.entities.UserAuthorityEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.UserRoleEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import com.ste4o26.cookviser_rest_api.repositories.AuthorityRepository;
import com.ste4o26.cookviser_rest_api.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInit {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public DataInit(RoleRepository roleRepository, AuthorityRepository authorityRepository) {
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }

    @PostConstruct
    private void populateDB() {
        if (this.authorityRepository.count() != 0 || this.roleRepository.count() != 0) {
            return;
        }

        List<UserAuthorityEntity> authorities = Arrays.stream(AuthorityName.values())
                .map(UserAuthorityEntity::new)
                .collect(Collectors.toList());

        List<UserRoleEntity> roles = Arrays.stream(RoleName.values())
                .map(UserRoleEntity::new)
                .collect(Collectors.toList());

        this.authorityRepository.saveAllAndFlush(authorities);
        this.roleRepository.saveAllAndFlush(roles);
    }
}
