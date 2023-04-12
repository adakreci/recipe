package com.abnamro.recipe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RecipeRequestDTO {
    private final Boolean vegetarian;
    private final Integer servings;
    private final String searchKey;
    private final List<String> includeIngredients;
    private final List<String> excludeIngredients;
}
