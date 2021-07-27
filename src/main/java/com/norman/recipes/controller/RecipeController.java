package com.norman.recipes.controller;

import com.norman.recipes.service.RecipeService;
import com.norman.recipes.service.dto.RecipeDetail;
import com.norman.recipes.service.dto.RecipeDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public RecipeDtoList getRecipesByPageSizeAndNum(@RequestParam int size, @RequestParam int page) {
        return recipeService.findBySizeAndPage(size, page);
    }

    @GetMapping("/{id}")
    public RecipeDetail getRecipeById(@PathVariable Long id){
        return recipeService.findById(id);
    }
}
