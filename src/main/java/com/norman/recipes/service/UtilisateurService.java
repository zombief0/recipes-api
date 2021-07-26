package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UtilisateurService extends UserDetailsService {
    Utilisateur findByEmail(String username);
}
