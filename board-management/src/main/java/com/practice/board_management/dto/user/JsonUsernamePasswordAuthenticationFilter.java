package com.practice.board_management.dto.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import jakarta.servlet.ServletException;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/board/users/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper;
    private static final String USERNAME_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD));
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!request.getRequestURI().equals(DEFAULT_LOGIN_REQUEST_URL)) {
            throw new AuthenticationServiceException("Unsupported login endpoint: " + request.getRequestURI());
        }

        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Content-Type must be application/json");
        }

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String> credentials = objectMapper.readValue(body, Map.class);

        String email = credentials.get(USERNAME_KEY);
        String password = credentials.get(PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
