package com.ste4o26.cookviser_rest_api.web.filters;

import com.ste4o26.cookviser_rest_api.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String HTTP_OPTIONS_METHOD = "OPTIONS";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final JWTUtil jwtUtil;

    @Autowired
    public JWTAuthorizationFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getMethod().equalsIgnoreCase(HTTP_OPTIONS_METHOD)) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = this.jwtUtil.getTokenSubject(token);

            if (this.jwtUtil.isValidToken(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> userAuthorities = this.jwtUtil.getUserAuthorities(token);
                Authentication authentication = this.jwtUtil.getAuthentication(username, userAuthorities, httpServletRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
