package com.ste4o26.cookviser_rest_api.web.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ste4o26.cookviser_rest_api.domain.HttpResponseModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ste4o26.cookviser_rest_api.init.ErrorMessages.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException exception) throws IOException, ServletException {
        HttpResponseModel httpResponseModel =
                new HttpResponseModel(UNAUTHORIZED.value(), UNAUTHORIZED.name(), UNAUTHORIZED.getReasonPhrase(), ACCESS_DENIED_MESSAGE);

        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(FORBIDDEN.value());

        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseOutputStream, httpResponseModel);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
