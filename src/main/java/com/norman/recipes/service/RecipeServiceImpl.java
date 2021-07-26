package com.norman.recipes.service;

import com.norman.recipes.domain.entities.Recipe;
import com.norman.recipes.domain.repositories.RecipeRepository;
import com.norman.recipes.service.dto.RecipeDto;
import com.norman.recipes.service.dto.RecipeDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

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
}
