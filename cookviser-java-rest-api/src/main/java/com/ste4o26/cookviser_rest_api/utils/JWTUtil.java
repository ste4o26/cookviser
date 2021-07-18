package com.ste4o26.cookviser_rest_api.utils;

import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JWTUtil {
    public static final String JWT_HEADER = "jwtToken";

    String generateToken(UserServiceModel userServiceModel);

    List<GrantedAuthority> getUserAuthorities(String token);

    Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest httpServletRequest);

    boolean isValidToken(String username, String token);

    String getTokenSubject(String token);
}
