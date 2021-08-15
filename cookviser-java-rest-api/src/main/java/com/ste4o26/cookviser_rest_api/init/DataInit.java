package com.ste4o26.cookviser_rest_api.init;

import com.ste4o26.cookviser_rest_api.domain.binding_models.UserRegisterBindingModel;
import com.ste4o26.cookviser_rest_api.domain.entities.*;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.AuthorityName;
import com.ste4o26.cookviser_rest_api.domain.entities.enums.RoleName;
import com.ste4o26.cookviser_rest_api.domain.service_models.RecipeServiceModel;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import com.ste4o26.cookviser_rest_api.exceptions.EmailAlreadyExistsException;
import com.ste4o26.cookviser_rest_api.exceptions.UsernameAlreadyExistsException;
import com.ste4o26.cookviser_rest_api.repositories.*;
import com.ste4o26.cookviser_rest_api.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.ste4o26.cookviser_rest_api.init.ImagesUrlConstant.*;
import static com.ste4o26.cookviser_rest_api.init.ImagesUrlConstant.ITALIAN_CUISINE_IMAGE_URL;

@Component
public class DataInit {


    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final CuisineRepository cuisineRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public DataInit(RoleRepository roleRepository,
                    AuthorityRepository authorityRepository,
                    CuisineRepository cuisineRepository,
                    UserService userService, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.cuisineRepository = cuisineRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
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
                new CuisineEntity("Japanese", JAPANESE_CUISINE_IMAGE_URL, new HashSet<>()),
                new CuisineEntity("Bulgarian", BULGARIAN_CUISINE_IMAGE_URL, new HashSet<>()),
                new CuisineEntity("German", GERMAN_CUISINE_IMAGE_URL, new HashSet<>()),
                new CuisineEntity("American", AMERICAN_CUISINE_IMAGE_URL, new HashSet<>()));

        this.authorityRepository.saveAllAndFlush(authorities);
        this.roleRepository.saveAllAndFlush(roles);
        this.cuisineRepository.saveAllAndFlush(cuisines);
        this.registerTestUsers();

    }

    private void registerTestUsers() throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RoleNotFoundException {
        List<UserRegisterBindingModel> allUsers = List.of(
                new UserRegisterBindingModel("ste4o26", "ste4o26@abv.bg", "Hey im here to share all my knowledge of different cuisines i have explored so far.", "123456", "123456"),
                new UserRegisterBindingModel("user1", "user1@gmail.com", "Hello i just move out to live alone and im here to learn some recipes so i can handle the cooking by myself.", "123456", "123456"),
                new UserRegisterBindingModel("user2", "user2@gmail.co,", "No one can cook better than me! I have been doing this since i was 5 years old.", "123456", "123456"),
                new UserRegisterBindingModel("user3", "user3@abv.bg", "Looking for some fancy recipes to fill my cook book with. ", "123456", "123456"),
                new UserRegisterBindingModel("user4", "user4@abv.bg", "Hello im newbie im trying to learn some recipes just for fun.", "123456", "123456"));

        List<UserServiceModel> collect = allUsers.stream()
                .map(userRegisterBindingModel -> this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class))
                .collect(Collectors.toList());

        for (UserServiceModel userServiceModel : collect) {
            this.userService.register(userServiceModel);
        }
    }
}
