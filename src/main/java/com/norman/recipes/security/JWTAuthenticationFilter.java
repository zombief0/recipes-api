package com.norman.recipes.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.norman.recipes.domain.entities.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginModel loginModel = null;
        try {
            loginModel = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(Objects.requireNonNull(loginModel).getUsername(),
                        loginModel.getPassword(), new ArrayList<>());

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Utilisateur utilisateur = (Utilisateur) authResult.getPrincipal();
        Date expiredDate = new Date(System.currentTimeMillis() + SecurityProperties.EXPIRES_IN);
        String token = JWT.create()
                .withSubject(utilisateur.getUsername())
                .withClaim("role", "ROLE_" + utilisateur.getRole())
                .withExpiresAt(expiredDate).sign(Algorithm.HMAC512(SecurityProperties.SECRET));
        AuthResponse authResponse = new AuthResponse(token,
                SecurityProperties.EXPIRES_IN,
                utilisateur.getRole(),
                utilisateur.getUsername(), utilisateur.getId());
        String jsonAuthResponse = new ObjectMapper().writeValueAsString(authResponse);
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        printWriter.print(jsonAuthResponse);
        printWriter.flush();
    }
}
