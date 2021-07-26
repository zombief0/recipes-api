package com.norman.recipes.controller;

import com.norman.recipes.domain.entities.Recipe;
import com.norman.recipes.service.RecipeService;
import com.norman.recipes.service.UtilisateurService;
import com.norman.recipes.service.dto.IngredientDto;
import com.norman.recipes.service.dto.RecipeDto;
import com.norman.recipes.service.dto.RecipeDtoList;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private RecipeService recipeService;

    @Captor
    private ArgumentCaptor<Integer> pageCaptor;

    @Captor
    private ArgumentCaptor<Integer> sizeCaptor;

    @Test
    void getRecipesPageAndSize() throws Exception {
        IngredientDto ingredient = new IngredientDto();
        ingredient.setAmount(6);
        ingredient.setName("Tomato");
        ingredient.setId(6L);
        RecipeDto recipe = new RecipeDto();
        recipe.setName("Name");
        recipe.setIngredients(Collections.singletonList(ingredient));
        RecipeDtoList recipeList = new RecipeDtoList(Collections.singletonList(recipe), 0, 10, 2, 20);
        given(recipeService.findBySizeAndPage(anyInt(), anyInt())).willReturn(recipeList);

        mockMvc.perform(get("/api/recipe")
                .queryParam("page", "0")
                .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0]", notNullValue()));

        then(recipeService).should().findBySizeAndPage(sizeCaptor.capture(), pageCaptor.capture());
        assertThat(sizeCaptor.getValue()).isEqualTo(10);
        assertThat(pageCaptor.getValue()).isEqualTo(0);
    }
}
