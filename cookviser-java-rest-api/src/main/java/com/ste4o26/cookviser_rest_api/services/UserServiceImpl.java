package com.ste4o26.cookviser_rest_api.services;

import com.ste4o26.cookviser_rest_api.domain.entities.UserEntity;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserAuthorityServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserRoleServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.*;
import com.ste4o26.cookviser_rest_api.repositories.UserRepository;
import com.ste4o26.cookviser_rest_api.services.interfaces.AuthorityService;
import com.ste4o26.cookviser_rest_api.services.interfaces.RateService;
import com.ste4o26.cookviser_rest_api.services.interfaces.RoleService;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName.*;
import static com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName.*;
import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.*;
import static com.ste4o26.cookviser_rest_api.init.ImagesUrlConstant.DEFAULT_PROFILE_IMAGE_URL;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityService authorityService;
    private final RateService rateService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           RoleService roleService,
                           BCryptPasswordEncoder bCryptPasswordEncoder, AuthorityService authorityService, RateService rateService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityService = authorityService;
        this.rateService = rateService;
    }

    private void isNewUser(UserServiceModel userServiceModel) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        try {
            this.fetchByUsername(userServiceModel.getUsername());
            throw new UsernameAlreadyExistsException(String
                    .format(USERNAME_ALREADY_EXISTS, userServiceModel.getUsername()));
        } catch (UsernameNotFoundException ignored) {
        }

        try {
            this.fetchByEmail(userServiceModel.getEmail());
            throw new EmailAlreadyExistsException(String
                    .format(EMAIL_ALREADY_EXISTS, userServiceModel.getEmail()));
        } catch (EmailNotFoundException ignored) {
        }
    }

    private void giveRole(UserServiceModel userServiceModel, RoleName roleName) throws RoleNotFoundException {
        UserRoleServiceModel role = this.roleService.fetchByName(roleName);
        userServiceModel.setRole(role);
    }

    private void giveAuthorities(UserServiceModel userServiceModel, AuthorityName... authorities) {
        Set<UserAuthorityServiceModel> userAuthorities = Arrays.stream(authorities).
                map(this.authorityService::fetchByName)
                .collect(Collectors.toSet());

        userServiceModel.setAuthorities(userAuthorities);
    }

    private void giveAuthorities(UserServiceModel userServiceModel) {
        Set<UserAuthorityServiceModel> allAuthorities = this.authorityService.fetchAll();
        userServiceModel.setAuthorities(allAuthorities);

    }

    @Override
    public UserServiceModel fetchByUsername(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String
                        .format(USERNAME_NOT_EXISTS, username)));

        return this.modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public UserServiceModel fetchByEmail(String email) throws EmailNotFoundException {
        UserEntity userEntity = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(String
                        .format(EMAIL_NOT_EXISTS, email)));

        return this.modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel)
            throws RoleNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException {
        this.isNewUser(userServiceModel);

        userServiceModel.setProfileImageUrl(DEFAULT_PROFILE_IMAGE_URL);
        userServiceModel.setMyRecipes(new HashSet<>());
        userServiceModel.setMyCookedRecipes(new HashSet<>());
        userServiceModel.setRates(new HashSet<>());

        if (this.userRepository.count() == 0) {
            this.giveRole(userServiceModel, ROLE_ADMIN);
            this.giveAuthorities(userServiceModel);
        } else {
            this.giveRole(userServiceModel, ROLE_USER);
            this.giveAuthorities(userServiceModel, READ, WRITE);
        }

        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        userEntity.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        UserEntity registeredUser = this.userRepository.saveAndFlush(userEntity);

        return this.modelMapper.map(registeredUser, UserServiceModel.class);
    }

    @Override
    public UserServiceModel update(UserServiceModel userServiceModel) {
        if (userServiceModel == null) {
            throw new IllegalArgumentException(REQUIRED_ARGUMENTS_NOT_PROVIDED);
        }

        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        UserEntity updated = this.userRepository.saveAndFlush(userEntity);

        return this.modelMapper.map(updated, UserServiceModel.class);
    }

    @Override
    public UserServiceModel promote(UserServiceModel userServiceModel) throws RoleNotFoundException, PromotionDeniedException {
        RoleName userRole = userServiceModel.getRole().getRole();
        if (userRole.equals(ROLE_MODERATOR) || userRole.equals(ROLE_ADMIN)) {
            throw new PromotionDeniedException(String.format(ALREADY_GOT_HIGHEST_ROLE, userServiceModel.getUsername()));
        }

        this.giveRole(userServiceModel, ROLE_MODERATOR);
        this.giveAuthorities(userServiceModel, READ, WRITE, UPDATE);

        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        UserEntity promotedUser = this.userRepository.saveAndFlush(userEntity);

        return this.modelMapper.map(promotedUser, UserServiceModel.class);
    }

    @Override
    public UserServiceModel demote(UserServiceModel userServiceModel) throws DemotionDeniedException, RoleNotFoundException {
        RoleName currentUserRole = userServiceModel.getRole().getRole();
        if (currentUserRole.equals(ROLE_ADMIN)) {
            throw new DemotionDeniedException(ADMIN_CANNOT_BE_DEMOTED);
        }

        if (currentUserRole.equals(ROLE_USER)) {
            throw new DemotionDeniedException(USER_CANNOT_BE_DEMOTED);
        }

        this.giveRole(userServiceModel, ROLE_USER);
        this.giveAuthorities(userServiceModel, READ, WRITE);

        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        UserEntity demotedUser = this.userRepository.saveAndFlush(userEntity);

        return this.modelMapper.map(demotedUser, UserServiceModel.class);
    }

    @Override
    public UserServiceModel addRecipeToMyRecipes(UserServiceModel publisher, RecipeServiceModel createdRecipe) {
        publisher.getMyRecipes().add(createdRecipe);

        UserEntity userEntity = this.modelMapper.map(publisher, UserEntity.class);
        UserEntity updatedUser = this.userRepository.saveAndFlush(userEntity);

        return this.modelMapper.map(updatedUser, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> fetchAll() {
        List<UserEntity> allUsers = this.userRepository.findAll();

        return allUsers.stream()
                .map(userEntity -> this.modelMapper.map(userEntity, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceModel> fetchBestThreeChefs() {
        List<UserServiceModel> allUsers = this.fetchAll();
        for (UserServiceModel user : allUsers) {
            double currentUserOverallRating = this.rateService.calculateUserOverallRate(user);
            user.setOverallRating(currentUserOverallRating);
        }

        return allUsers.stream()
                .sorted((first, second) -> Double.compare(second.getOverallRating(), first.getOverallRating()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
