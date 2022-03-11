package com.norman.recipes.security;

import com.norman.recipes.domain.entities.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DetailsUtilisateurService extends UserDetailsService {
    Utilisateur findByEmail(String username);
}
