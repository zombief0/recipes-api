package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.service.dto.UtilisateurDto;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserStatus saveUser(UtilisateurDto utilisateurDto);

    UserStatus activateUser(Long id);

    Utilisateur userFromAuth(Authentication authentication);
}
