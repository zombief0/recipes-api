package com.norman.recipes.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RecipeSaveDto {
    private Long id;
    private String name;

    private String imagePath;
    private String description;
    private List<IngredientDto> ingredients;
    private Long idUser;
}
