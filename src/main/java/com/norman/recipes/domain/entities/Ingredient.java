package com.norman.recipes.domain.entities;

import com.norman.recipes.service.dto.IngredientDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ingredient extends BaseEntity{
    private String name;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Ingredient (IngredientDto ingredientDto){
        this.name = ingredientDto.getName();
        this.amount = ingredientDto.getAmount();
    }
}
