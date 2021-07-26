package com.norman.recipes.service;

import com.norman.recipes.service.dto.RecipeDtoList;

public interface RecipeService {
    RecipeDtoList findBySizeAndPage(int size, int page);
}
