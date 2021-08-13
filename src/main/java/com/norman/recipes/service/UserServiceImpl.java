package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.domain.repositories.UtilisateurRepository;
import com.norman.recipes.service.dto.UtilisateurDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserStatus saveUser(UtilisateurDto utilisateurDto) {
        Optional<Utilisateur> userInDB = utilisateurRepository.findByEmail(utilisateurDto.getEmail());
        if (userInDB.isPresent()){
            if (!userInDB.get().isActive()){
                return UserStatus.USER_NOT_ACTIVE;
            }

            return UserStatus.EMAIL_ALREADY_EXISTS;
        }
        utilisateurDto.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        Utilisateur utilisateur = new Utilisateur(utilisateurDto);
        utilisateurRepository.save(utilisateur);
//        emailService.sendActivationMail(utilisateur.getEmail(), utilisateur.getId());
        return UserStatus.SUCCES;
    }

    @Override
    public UserStatus activateUser(Long id) {
        Optional<Utilisateur> userOp = utilisateurRepository.findById(id);
        if (userOp.isPresent()){
            Utilisateur utilisateur = userOp.get();
            if (utilisateur.isActive()) {
                return UserStatus.USER_ALREADY_ACTIVE;
            }

            utilisateur.setActive(true);
            utilisateurRepository.save(utilisateur);
            return UserStatus.USER_ACTIVATED;
        }

        return UserStatus.USER_NOT_FOUND;
    }

    @Override
    public Utilisateur userFromAuth(Authentication authentication) {

        return utilisateurRepository.findByEmail(authentication.getName()).get();
    }
}
