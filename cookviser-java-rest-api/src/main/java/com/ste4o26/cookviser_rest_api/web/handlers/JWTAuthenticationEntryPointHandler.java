package com.ste4o26.cookviser_rest_api.web.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ste4o26.cookviser_rest_api.domain.HttpResponseModel;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JWTAuthenticationEntryPointHandler extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException exception) throws IOException {
        HttpResponseModel httpResponseModel =
                new HttpResponseModel(FORBIDDEN.value(), FORBIDDEN.name(), FORBIDDEN.getReasonPhrase(), FORBIDDEN_MESSAGE);

        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(FORBIDDEN.value());

        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseOutputStream, httpResponseModel);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
