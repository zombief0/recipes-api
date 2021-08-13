package com.norman.recipes.domain.repositories;

import com.norman.recipes.domain.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    void deleteUtilisateurByEmail(String email);
}
