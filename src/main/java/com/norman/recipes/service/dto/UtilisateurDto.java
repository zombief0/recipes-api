package com.norman.recipes.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UtilisateurDto {
    private String email;
    private String nom;
    private String password;
}
