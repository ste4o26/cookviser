package com.ste4o26.cookviser_rest_api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ste4o26.cookviser_rest_api.domain.service_models.UserServiceModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtilImpl implements JWTUtil {
    public static final long EXPIRATION_TIME = 3_600_000;
    public static final String ISSUER = "COOKVISER_LTD";
    public static final String AUDIANCE = "USERS_RECEPIECES_PORTAL";
    public static final String AUTHORITIES = "authorities";

    @Value("jwt.secret")
    private String secret;

    //TODO razgledai go pak i go razberii!!!!!!!!!!
    private String[] getTokenClaims(String token) {
        JWTVerifier jwtVerifier = this.getJWTVerifier();

        return jwtVerifier
                .verify(token)
                .getClaim(AUTHORITIES)
                .asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier jwtVerifier;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            jwtVerifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
        } catch (JWTVerificationException ve) {
            throw new JWTVerificationException("Token can not be verified!");
        }

        return jwtVerifier;
    }

    private String[] getUserClaims(UserServiceModel userServiceModel) {
        return userServiceModel.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority().name())
                .toArray(String[]::new);
    }

    private boolean isTokenExpired(String token, JWTVerifier jwtVerifier) {
        Date expiresAt = jwtVerifier.verify(token).getExpiresAt();
        return expiresAt.after(new Date());
    }

    //This method executes after the credentials are checked by spring security!!!
    @Override
    public String generateToken(UserServiceModel userServiceModel) {
        String[] userClaims = this.getUserClaims(userServiceModel);

        return JWT.create()
                .withIssuer(ISSUER)
                .withAudience(AUDIANCE)
                .withIssuedAt(new Date())
                .withSubject(userServiceModel.getUsername())
                .withArrayClaim(AUTHORITIES, userClaims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public List<GrantedAuthority> getUserAuthorities(String token) {
        String[] claims = this.getTokenClaims(token);

        return Arrays.stream(claims)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest httpServletRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean isValidToken(String username, String token) {
        JWTVerifier jwtVerifier = this.getJWTVerifier();
        return (username != null && !username.trim().isEmpty()) || !this.isTokenExpired(token, jwtVerifier);
    }

    @Override
    public String getTokenSubject(String token) {
        JWTVerifier jwtVerifier = this.getJWTVerifier();
        return jwtVerifier.verify(token).getSubject();
    }
}
