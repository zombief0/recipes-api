package com.norman.recipes.security;

import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.domain.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements DetailsUtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return utilisateurRepository
                .findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur " + s + "invalide"));
    }

    @Override
    public Utilisateur findByEmail(String username) {
        return utilisateurRepository.findByEmail(username).orElse(null);
    }
}
