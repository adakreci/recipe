package com.abnamro.recipe.mapper;

import com.abnamro.recipe.domain.enumeration.IngredientUnit;
import com.abnamro.recipe.dto.enumeration.MeasureUnit;
import org.mapstruct.EnumMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface UnitMapper {

    @EnumMapping(nameTransformationStrategy = "suffix", configuration = "_UNIT")
    MeasureUnit map(final IngredientUnit unit);

    @InheritInverseConfiguration
    IngredientUnit map(MeasureUnit unit);
}
