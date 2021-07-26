package com.norman.recipes.service.dto;

import com.norman.recipes.domain.entities.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class RecipeDto {
    private Long id;
    private String name;

    private String description;

    private String imagePath;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private Long idUser;
    private String username;
    private List<IngredientDto> ingredients;

    public RecipeDto(Recipe recipe){
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.createdDate = recipe.getCreatedDate();
        this.lastModifiedDate = recipe.getLastModifiedDate();
        this.idUser = recipe.getUtilisateur().getId();
        this.description = recipe.getDescription();
        this.imagePath = recipe.getImagePath();
        this.ingredients = recipe.getIngredients()
                .stream().map(IngredientDto::new).collect(Collectors.toList());
        this.username = recipe.getUtilisateur().getNom();
    }
}
