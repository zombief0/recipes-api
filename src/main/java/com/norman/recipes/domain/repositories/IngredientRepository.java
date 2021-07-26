package com.norman.recipes.domain.repositories;

import com.norman.recipes.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
