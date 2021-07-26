package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.domain.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public Utilisateur findByEmail(String username) {
        return utilisateurRepository.findByEmail(username);
    }
}
