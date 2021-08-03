package com.ste4o26.cookviser_rest_api.init;

import com.ste4o26.cookviser_rest_api.domain.entities.*;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.EmailAlreadyExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.UsernameAlreadyExistsException;
import com.ste4o26.cookviser_rest_api.repositories.*;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInit {
    public static final String ITALIAN_CUISINE_IMAGE_URL = "https://res.cloudinary.com/ste4o26/image/upload/v1627850811/cookviser/cuisine1_dcfrnc.jpg";
    public static final String FRENCH_CUISINE_IMAGE_URL = "https://res.cloudinary.com/ste4o26/image/upload/v1627850811/cookviser/cuisine2_kdppw8.jpg";
    public static final String JAPANESE_CUISINE_IMAGE_URL = "https://res.cloudinary.com/ste4o26/image/upload/v1627850811/cookviser/cuisine3_pxljlv.jpg";

    public static final String DEFAULT_PROFILE_IMAGE_URL
            = "https://res.cloudinary.com/ste4o26/image/upload/v1627835470/cookviser/default-profile-picture_zskelz.jpg";

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final CuisineRepository cuisineRepository;
    private final UserService userService;

    @Autowired
    public DataInit(RoleRepository roleRepository,
                    AuthorityRepository authorityRepository,
                    CuisineRepository cuisineRepository,
                    UserService userService) {
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.cuisineRepository = cuisineRepository;
        this.userService = userService;
    }

    @PostConstruct
    private void populateDB() throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RoleNotFoundException {
        if (this.authorityRepository.count() != 0 ||
                this.roleRepository.count() != 0 ||
                this.cuisineRepository.count() != 0) {
            return;
        }

        List<UserAuthorityEntity> authorities = Arrays.stream(AuthorityName.values())
                .map(UserAuthorityEntity::new)
                .collect(Collectors.toList());

        List<UserRoleEntity> roles = Arrays.stream(RoleName.values())
                .map(UserRoleEntity::new)
                .collect(Collectors.toList());

        List<CuisineEntity> cuisines = List.of(
                new CuisineEntity("French", FRENCH_CUISINE_IMAGE_URL, new HashSet<>()),
                new CuisineEntity("Italian", ITALIAN_CUISINE_IMAGE_URL, new HashSet<>()),
                new CuisineEntity("Japanese", JAPANESE_CUISINE_IMAGE_URL, new HashSet<>())
        );

        this.authorityRepository.saveAllAndFlush(authorities);
        this.roleRepository.saveAllAndFlush(roles);
        this.cuisineRepository.saveAllAndFlush(cuisines);

        this.registerTestUser();

    }

    private void registerTestUser() throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RoleNotFoundException {
        UserServiceModel user = new UserServiceModel();
        user.setUsername("ste4o26");
        user.setEmail("ste4o26@abv.bg");
        user.setPassword("123456");
        user.setDescription("Some Dummy description.");

        this.userService.register(user);
    }
}
