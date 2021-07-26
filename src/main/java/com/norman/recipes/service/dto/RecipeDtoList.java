package com.norman.recipes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeDtoList {
    private List<RecipeDto> recipes;
    private int currentPage;
    private int size;
    private int totalPages;
    private long totalElements;
}
