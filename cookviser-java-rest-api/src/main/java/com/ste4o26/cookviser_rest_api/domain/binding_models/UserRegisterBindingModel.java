package com.ste4o26.cookviser_rest_api.domain.binding_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterBindingModel {
    private String username;
    private String email;
    private String description;
    private String password;
    private String confirmPassword;
}
