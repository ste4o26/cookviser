package com.ste4o26.cookviser_rest_api.repositories;

import com.ste4o26.cookviser_rest_api.domain.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
