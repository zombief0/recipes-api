package com.norman.recipes.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, DetailsUtilisateurService utilisateurService) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String headerToken = request.getHeader("Authorization");


        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            try {
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityProperties.SECRET))
                        .build().verify(headerToken.replace("Bearer ", ""));
                String role = decodedJWT.getClaim("role").asString();
                String subject = decodedJWT.getSubject();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(subject, null, Collections.singletonList(grantedAuthority));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception e) {
                System.out.println("Token invalide " + e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
