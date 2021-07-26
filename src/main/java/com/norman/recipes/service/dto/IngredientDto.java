package com.norman.recipes.service.dto;

import com.norman.recipes.domain.entities.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IngredientDto {
    private Long id;
    private String name;
    private double amount;

    public IngredientDto(Ingredient ingredient){
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.amount = ingredient.getAmount();
    }
}
