package com.abnamro.recipe.mapper;

import com.abnamro.recipe.domain.enumeration.IngredientUnit;
import com.abnamro.recipe.dto.enumeration.MeasureUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitMapperImpl.class)
public class UnitMapperTest {

    @Autowired
    private UnitMapper unitMapperTest;


    @Test
    public void test_map_ingredient_unit() {
        var unit = unitMapperTest.map(MeasureUnit.GR_UNIT);
        assertEquals(unit, IngredientUnit.GR);

        unit = unitMapperTest.map(MeasureUnit.MG_UNIT);
        assertEquals(unit, IngredientUnit.MG);

        unit = unitMapperTest.map(MeasureUnit.KG_UNIT);
        assertEquals(unit, IngredientUnit.KG);

        unit = unitMapperTest.map(MeasureUnit.PIECE_UNIT);
        assertEquals(unit, IngredientUnit.PIECE);
    }

    @Test
    public void test_map_measure_unit() {
        var unit = unitMapperTest.map(IngredientUnit.GR);
        assertEquals(unit, MeasureUnit.GR_UNIT);

        unit = unitMapperTest.map(IngredientUnit.MG);
        assertEquals(unit, MeasureUnit.MG_UNIT);

        unit = unitMapperTest.map(IngredientUnit.KG);
        assertEquals(unit, MeasureUnit.KG_UNIT);

        unit = unitMapperTest.map(IngredientUnit.PIECE);
        assertEquals(unit, MeasureUnit.PIECE_UNIT);
    }

}
