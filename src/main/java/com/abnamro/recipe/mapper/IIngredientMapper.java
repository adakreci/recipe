package com.abnamro.recipe.mapper;

import com.abnamro.recipe.domain.Ingredient;
import com.abnamro.recipe.domain.RecipeIngredient;
import com.abnamro.recipe.dto.IngredientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(uses = {UnitMapper.class})
public interface IIngredientMapper {

    IngredientDTO mapToDTO(final Ingredient ingredient);
    List<IngredientDTO> mapToDTO(final List<Ingredient> ingredient);

    @Mapping(target = "ingredientId", source = "ingredient.ingredientId")
    IngredientDTO mapToDTO(final RecipeIngredient recipe);

    @Mapping(target = "ingredient.ingredientId", source = "ingredientId")
    RecipeIngredient mapToDomain(final IngredientDTO recipe);

    @Mapping(target = "ingredient.ingredientId", source = "ingredientId")
    Set<RecipeIngredient> mapToDomains(final Set<IngredientDTO> recipes);

    @Mappings({
            @Mapping(source = "ingredientRequest.measure", target = "measure"),
            @Mapping(source = "ingredientRequest.unit", target = "unit")
    })
    RecipeIngredient map(final IngredientDTO ingredientRequest, @MappingTarget RecipeIngredient recipeIngredient);

}
