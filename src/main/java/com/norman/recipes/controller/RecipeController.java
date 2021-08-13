package com.norman.recipes.controller;

import com.norman.recipes.domain.entities.Utilisateur;
import com.norman.recipes.service.RecipeService;
import com.norman.recipes.service.UserService;
import com.norman.recipes.service.dto.RecipeDeleteDto;
import com.norman.recipes.service.dto.RecipeDetail;
import com.norman.recipes.service.dto.RecipeDtoList;
import com.norman.recipes.service.dto.RecipeSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping
    public RecipeDtoList getRecipesByPageSizeAndNum(@RequestParam int size, @RequestParam int page) {
        return recipeService.findBySizeAndPage(size, page);
    }

    @GetMapping("/{id}")
    public RecipeDetail getRecipeById(@PathVariable Long id){
        return recipeService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public void saveRecipe(Authentication authentication, @RequestBody RecipeSaveDto recipe){
        recipeService.saveRecipe(authentication, recipe);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public ResponseEntity<?> updateRecipe(Authentication authentication, @RequestBody RecipeSaveDto recipe){
        Utilisateur utilisateur = userService.userFromAuth(authentication);
        if (utilisateur.getId().equals(recipe.getIdUser()) || utilisateur.getRole().equals("ADMIN")){
            recipeService.updateRecipe(recipe);
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping()
    public ResponseEntity<?> deleteRecipe(Authentication authentication, @RequestBody RecipeDeleteDto recipeDeleteModel){
        Utilisateur utilisateur = userService.userFromAuth(authentication);
        if (utilisateur.getId().equals(recipeDeleteModel.getIdUser()) || utilisateur.getRole().equals("ADMIN")){
            recipeService.deleteRecipe(recipeDeleteModel.getIdRecipe());
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
