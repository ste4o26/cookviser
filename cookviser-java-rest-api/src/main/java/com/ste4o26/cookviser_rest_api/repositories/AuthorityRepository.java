package com.ste4o26.cookviser_rest_api.repositories;

import com.ste4o26.cookviser_rest_api.domain.entities.UserAuthorityEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<UserAuthorityEntity, String> {
    Optional<UserAuthorityEntity> findByAuthority(AuthorityName name);
}
