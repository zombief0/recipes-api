package com.norman.recipes.service;

import com.norman.recipes.service.dto.RecipeDetail;
import com.norman.recipes.service.dto.RecipeDtoList;
import com.norman.recipes.service.dto.RecipeSaveDto;
import org.springframework.security.core.Authentication;

public interface RecipeService {
    RecipeDtoList findBySizeAndPage(int size, int page);

    RecipeDetail findById(Long id);

    void saveRecipe(Authentication authentication, RecipeSaveDto recipe);

    void updateRecipe(RecipeSaveDto recipe);

    void deleteRecipe(Long id);
}
