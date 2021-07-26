package com.norman.recipes.domain.repositories;

import com.norman.recipes.domain.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
