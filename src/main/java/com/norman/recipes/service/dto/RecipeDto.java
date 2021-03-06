package com.norman.recipes.service.dto;

import com.norman.recipes.domain.entities.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class RecipeDto {
    private Long id;
    private String name;

    private String imagePath;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private Long idUser;
    private String username;

    public RecipeDto(Recipe recipe){
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.createdDate = recipe.getCreatedDate();
        this.lastModifiedDate = recipe.getLastModifiedDate();
        this.idUser = recipe.getUtilisateur().getId();
        this.imagePath = recipe.getImagePath();
        this.username = recipe.getUtilisateur().getNom();
    }
}
