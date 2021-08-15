package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.UserAuthorityEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserAuthorityServiceModel;
import com.ste4o26.cookviser_rest_api.repositories.AuthorityRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.AuthorityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.AUTHORITY_NOT_SUPPORTED;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<UserAuthorityServiceModel> fetchAll() {
        List<UserAuthorityEntity> authorities = this.authorityRepository.findAll();

        return authorities
                .stream()
                .map(authority -> this.modelMapper.map(authority, UserAuthorityServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public UserAuthorityServiceModel fetchByName(AuthorityName authorityName) {
        UserAuthorityEntity authority = this.authorityRepository
                .findByAuthority(authorityName)
                .orElseThrow(() -> new IllegalArgumentException(String.format(AUTHORITY_NOT_SUPPORTED, authorityName.name())));

        return this.modelMapper.map(authority, UserAuthorityServiceModel.class);
    }
}
