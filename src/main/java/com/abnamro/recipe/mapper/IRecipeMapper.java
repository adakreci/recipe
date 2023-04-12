package com.abnamro.recipe.mapper;

import com.abnamro.recipe.domain.Recipe;
import com.abnamro.recipe.dto.RecipeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(uses = {IIngredientMapper.class})
public interface IRecipeMapper {

    RecipeDTO map(final Recipe recipe);

    List<RecipeDTO> map(final List<Recipe> recipe);

    Recipe map(final RecipeDTO recipe);

    @Mappings({
            @Mapping(source = "requestRecipe.name", target = "name"),
            @Mapping(source = "requestRecipe.instructions", target = "instructions"),
            @Mapping(source = "requestRecipe.servings", target = "servings"),
            @Mapping(source = "requestRecipe.vegetarian", target = "vegetarian"),
            @Mapping(target = "recipe.ingredients", ignore = true),
            @Mapping(target = "recipeId", ignore = true)
    })
    Recipe map(final RecipeDTO requestRecipe, @MappingTarget Recipe recipe);

}
