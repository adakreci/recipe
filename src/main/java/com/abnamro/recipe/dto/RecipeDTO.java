package com.abnamro.recipe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "The model view of the recipe")
public class RecipeDTO {

    @ApiModelProperty(value = "The id of the recipe")
    private Long recipeId;

    @ApiModelProperty(value = "The unique name of the recipe")
    private String name;

    @ApiModelProperty(value = "The instructions for a recipe")
    private String instructions;

    @ApiModelProperty(value = "Indicates for how many person is to be cooked")
    private Integer servings;

    @ApiModelProperty(value = "Indicates if the recipe is vegetarian")
    private Boolean vegetarian;

    @ApiModelProperty(value = "The ingredients necessary to prepare the recipe")
    private Set<IngredientDTO> ingredients;
}
