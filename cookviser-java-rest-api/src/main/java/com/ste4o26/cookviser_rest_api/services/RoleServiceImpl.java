package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.UserRoleEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserRoleServiceModel;
import com.ste4o26.cookviser_rest_api.repositories.RoleRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.ROLE_NOT_EXISTS;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleServiceModel fetchByName(RoleName name) throws RoleNotFoundException {
        UserRoleEntity userRoleEntity = this.roleRepository.findByRole(name)
                .orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_EXISTS, name)));

        return this.modelMapper.map(userRoleEntity, UserRoleServiceModel.class);
    }
}
