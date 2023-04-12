package com.abnamro.recipe.mapper;

import com.abnamro.recipe.domain.Ingredient;
import com.abnamro.recipe.domain.RecipeIngredient;
import com.abnamro.recipe.domain.enumeration.IngredientUnit;
import com.abnamro.recipe.dto.IngredientDTO;
import com.abnamro.recipe.dto.enumeration.MeasureUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IIngredientMapperImpl.class, UnitMapperImpl.class})
public class IngredientMapperTest {

    @Autowired
    private IIngredientMapper mapper;

    @Test
    public void test_map_ingredient_dto() {
        var expected = IngredientDTO.builder()
                .ingredientId(1L)
                .unit(MeasureUnit.GR_UNIT)
                .measure(2.0)
                .build();

        var mapped = mapper.mapToDTO(
                RecipeIngredient.builder()
                        .ingredient(
                                Ingredient.builder()
                                        .ingredientId(1L)
                                        .build())
                        .measure(2.0)
                        .unit(IngredientUnit.GR)
                        .build()
        );

        assertEquals(mapped.getIngredientId(), expected.getIngredientId());
        assertEquals(mapped.getUnit(), expected.getUnit());
        assertEquals(mapped.getMeasure(), expected.getMeasure());
    }

    @Test
    public void test_map_ingredient_domain() {
        var expected = RecipeIngredient.builder()
                .ingredient(
                        Ingredient.builder()
                                .ingredientId(1L)
                                .build())
                .measure(2.0)
                .unit(IngredientUnit.GR)
                .build();

        var mapped = mapper.mapToDomain(
                IngredientDTO.builder()
                        .ingredientId(1L)
                        .unit(MeasureUnit.GR_UNIT)
                        .measure(2.0)
                        .build()
        );

        assertEquals(mapped.getIngredient().getIngredientId(), expected.getIngredient().getIngredientId());
        assertEquals(mapped.getUnit(), expected.getUnit());
        assertEquals(mapped.getMeasure(), expected.getMeasure());
    }

    @Test
    public void test_map_ingredient_domain_from_different_source() {
        var expected = RecipeIngredient.builder()
                .ingredient(
                        Ingredient.builder()
                                .ingredientId(1L)
                                .name("Ingredient 1")
                                .healthBenefit("The healthBenefits of the ingredient")
                                .build())
                .measure(2.0)
                .unit(IngredientUnit.GR)
                .build();


        var mapped = mapper.map(
                IngredientDTO.builder()
                        .ingredientId(1L)
                        .unit(MeasureUnit.GR_UNIT)
                        .measure(2.0)
                        .build(),
                RecipeIngredient.builder()
                        .ingredient(
                                Ingredient.builder()
                                        .ingredientId(1L)
                                        .name("Ingredient 1")
                                        .healthBenefit("The healthBenefits of the ingredient")
                                        .build())
                        .measure(2.0)
                        .unit(IngredientUnit.GR)
                        .build()
        );

        assertEquals(mapped.getIngredient().getIngredientId(), expected.getIngredient().getIngredientId());
        assertEquals(mapped.getIngredient().getName(), expected.getIngredient().getName());
        assertEquals(mapped.getIngredient().getHealthBenefit(), expected.getIngredient().getHealthBenefit());
        assertEquals(mapped.getUnit(), expected.getUnit());
        assertEquals(mapped.getMeasure(), expected.getMeasure());
        assertEquals(mapped.getMeasure(), expected.getMeasure());
    }


}
