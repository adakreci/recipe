package com.abnamro.recipe.dto;

import com.abnamro.recipe.dto.enumeration.MeasureUnit;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "The model view of the ingredient")
public class IngredientDTO {

    @ApiModelProperty(value = "The id of the ingredient")
    private Long ingredientId;

    @ApiModelProperty(value = "The measure of the ingredient")
    private Double measure;

    @ApiModelProperty(value = "The unit of ingredient's measure")
    private MeasureUnit unit;
}
