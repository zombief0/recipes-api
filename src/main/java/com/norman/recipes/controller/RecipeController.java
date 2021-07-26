package com.norman.recipes.controller;

import com.norman.recipes.service.RecipeService;
import com.norman.recipes.service.dto.RecipeDto;
import com.norman.recipes.service.dto.RecipeDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;
    @GetMapping
    public RecipeDtoList getRecipesByPageSizeAndNum(@RequestParam int size, @RequestParam int page){
        return recipeService.findBySizeAndPage(size, page);
    }
}
