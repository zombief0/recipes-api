package com.norman.recipes.domain.entities;

import com.norman.recipes.service.dto.UtilisateurDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Utilisateur extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;
    private String nom;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
    private boolean active;

    @OneToMany(mappedBy = "utilisateur", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Recipe> recipes = new ArrayList<>();

    public Utilisateur(UtilisateurDto utilisateurDto){
        this.active = true;
        this.role = "USER";
        this.nom = utilisateurDto.getNom();
        this.email = utilisateurDto.getEmail();
        this.password = utilisateurDto.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.role);
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
