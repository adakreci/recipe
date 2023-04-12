package com.abnamro.recipe.service;

import com.abnamro.recipe.domain.Recipe;
import com.abnamro.recipe.dto.IngredientDTO;
import com.abnamro.recipe.dto.RecipeDTO;
import com.abnamro.recipe.repository.IngredientRepository;
import com.abnamro.recipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeValidationService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final WebMessageResolverService webMessageResolverService;

    public String validateRecipeCreate(final RecipeDTO recipe) {
        final boolean recipeExists = recipeRepository.existsByNameIgnoreCase(recipe.getName());
        if (recipeExists) {
            return webMessageResolverService.getMessage("Exists.recipe.name", recipe.getName());
        }
        return validateIngredients(recipe);
    }

    public String validateRecipeUpdate(final Recipe recipe,
                                       final RecipeDTO recipeRequest) {
        if (ObjectUtils.notEqual(recipe.getName(), recipeRequest.getName())) {
            final boolean recipeExists = recipeRepository.existsByNameIgnoreCase(recipeRequest.getName());
            if (recipeExists) {
                return webMessageResolverService.getMessage("Exists.recipe.name", recipeRequest.getName());
            }
        }
        return validateIngredients(recipeRequest);
    }

    private String validateIngredients(final RecipeDTO recipeRequest) {
        for (final IngredientDTO ingredient : recipeRequest.getIngredients()) {
            final boolean ingredientExists = ingredientRepository.existsById(ingredient.getIngredientId());
            if (ingredientExists) {
                continue;
            }
            return webMessageResolverService.getMessage("NotExists.ingredient.message", ingredient.getIngredientId());
        }
        return null;
    }

}
