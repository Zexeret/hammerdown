package com.site.hammerdown.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.hammerdown.model.APIResponseStatus;
import com.site.hammerdown.payload.APIResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", "User does not have permission to access this resource.");
        responseData.put("path", request.getRequestURI());

        APIResponse apiResponse = APIResponse.builder()
                .data(responseData)
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                .status(APIResponseStatus.FAILURE)
                .errorMessage("Forbidden")
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), apiResponse);
    }
}