package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Ingredient;
import com.norman.recipes.domain.entities.Recipe;
import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.domain.repositories.RecipeRepository;
import com.norman.recipes.service.dto.RecipeDetail;
import com.norman.recipes.service.dto.RecipeDtoList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith({MockitoExtension.class})
class RecipeServiceTest {
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Test
    void findRecipesWithSizeAndPage() {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(6);
        ingredient.setName("Tomato");
        ingredient.setId(6L);
        Recipe recipe = new Recipe();
        recipe.setName("Name");
        recipe.setUtilisateur(new Utilisateur());
        recipe.setIngredients(Collections.singletonList(ingredient));
        Page<Recipe> recipePage = new PageImpl<>(Collections.singletonList(recipe));
        given(recipeRepository.findAll(any(Pageable.class))).willReturn(recipePage);

        RecipeDtoList recipeList = recipeService.findBySizeAndPage(10, 0);

        then(recipeRepository).should().findAll(any(Pageable.class));
        assertThat(recipeList).isNotNull();
    }

    @Test
    void findRecipeById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(6);
        ingredient.setName("Tomato");
        ingredient.setId(6L);
        Recipe recipe = new Recipe();
        recipe.setId(3L);
        recipe.setName("Name");
        recipe.setUtilisateur(new Utilisateur());
        recipe.setIngredients(Collections.singletonList(ingredient));

        given(recipeRepository.findById(anyLong())).willReturn(Optional.of(recipe));

        RecipeDetail foundRecipe = recipeService.findById(3L);

        then(recipeRepository).should().findById(anyLong());
        assertThat(foundRecipe).isNotNull();
    }
}
