package com.norman.recipes.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.service.UtilisateurService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UtilisateurService utilisateurService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UtilisateurService utilisateurService) {
        super(authenticationManager);
        this.utilisateurService = utilisateurService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String headerToken = request.getHeader("Authorization");


        if (headerToken == null || !headerToken.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        String username = JWT.require(Algorithm.HMAC512(SecurityProperties.SECRET))
                .build().verify(headerToken.replace("Bearer ",""))
                .getSubject();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if (username != null){
            Utilisateur utilisateur = utilisateurService.findByEmail(username);

             usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                     utilisateur.getUsername(), null, utilisateur.getAuthorities()
             );
        }

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }
}
