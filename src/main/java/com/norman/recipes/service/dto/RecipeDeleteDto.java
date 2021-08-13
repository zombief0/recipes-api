package com.norman.recipes.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecipeDeleteDto {
    private Long idRecipe;
    private Long idUser;
}
