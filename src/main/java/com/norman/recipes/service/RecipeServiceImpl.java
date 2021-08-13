package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Ingredient;
import com.norman.recipes.domain.entities.Recipe;
import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.domain.repositories.IngredientRepository;
import com.norman.recipes.domain.repositories.RecipeRepository;
import com.norman.recipes.domain.repositories.UtilisateurRepository;
import com.norman.recipes.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public RecipeDtoList findBySizeAndPage(int size, int page) {
        Page<Recipe> recipePage = recipeRepository
                .findAll(PageRequest.of(page,
                        size,
                        Sort.by(Sort.Direction.DESC, "lastModifiedDate")));
        List<RecipeDto> recipes = recipePage.get().map(RecipeDto::new).collect(Collectors.toList());
        return new RecipeDtoList(recipes,
                recipePage.getNumber(),
                recipePage.getSize(),
                recipePage.getTotalPages(),
                recipePage.getTotalElements());
    }

    @Override
    public RecipeDetail findById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        return optionalRecipe.map(RecipeDetail::new).orElse(null);
    }

    @Override
    public void saveRecipe(Authentication authentication, RecipeSaveDto recipe) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(authentication.getName()).get();
        Recipe recipeToSave = new Recipe(recipe);
        recipeToSave.setUtilisateur(utilisateur);
        recipeRepository.save(recipeToSave);
        saveIngredients(recipeToSave, recipe.getIngredients());
    }

    @Override
    public void updateRecipe(RecipeSaveDto recipe) {
        Optional<Recipe> recipeEntityOp = recipeRepository.findById(recipe.getId());
        recipeEntityOp.ifPresent(recipeEntity -> {
            recipeEntity.setName(recipe.getName());
            recipeEntity.setDescription(recipe.getDescription());
            recipeEntity.setImagePath(recipe.getImagePath());
            ingredientRepository.deleteAll(recipeEntity.getIngredients());
            saveIngredients(recipeEntity, recipe.getIngredients());
        });
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    private void saveIngredients(Recipe recipe, List<IngredientDto> ingredients){
        ingredients.forEach(ingredientModel -> {
            Ingredient ingredient = new Ingredient(ingredientModel);
            ingredient.setRecipe(recipe);
            ingredientRepository.save(ingredient);
        });
    }
}
