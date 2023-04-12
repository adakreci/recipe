package com.abnamro.recipe.mapper;

import com.abnamro.recipe.domain.Recipe;
import com.abnamro.recipe.dto.RecipeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IRecipeMapperImpl.class, IIngredientMapperImpl.class, UnitMapperImpl.class})
public class RecipeMapperTest {

    @Autowired
    private IRecipeMapper mapper;

    @Test
    public void test_map_recipe_from_multiple_sources() {
        var expected = Recipe.builder()
                .recipeId(2L)
                .name("Grandmother recipe")
                .instructions("Follow grandmother instructions")
                .servings(2)
                .vegetarian(Boolean.FALSE)
                .build();
        var mapped = mapper.map(
                RecipeDTO.builder()
                        .name("Grandmother recipe")
                        .instructions("Follow grandmother instructions")
                        .servings(2)
                        .vegetarian(Boolean.FALSE)
                        .build(),
                Recipe.builder()
                        .recipeId(2L)
                        .build()
        );

        assertEquals(mapped.getName(), expected.getName());
        assertEquals(mapped.getInstructions(), expected.getInstructions());
        assertEquals(mapped.getServings(), expected.getServings());
        assertEquals(mapped.getVegetarian(), expected.getVegetarian());
        assertEquals(mapped.getRecipeId(), expected.getRecipeId());
    }
}
