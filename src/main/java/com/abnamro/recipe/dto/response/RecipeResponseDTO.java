package com.abnamro.recipe.dto.response;

import com.abnamro.recipe.dto.RecipeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@ApiModel(value = "The model view with recipes and message")
public class RecipeResponseDTO {

    @ApiModelProperty(value = "The message to be shown after a processed action")
    private String message;

    @ApiModelProperty(value = "The recipes retrieved")
    private List<RecipeDTO> recipes;
}
