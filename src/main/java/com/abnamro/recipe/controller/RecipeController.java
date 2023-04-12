package com.abnamro.recipe.controller;

import com.abnamro.recipe.dto.RecipeDTO;
import com.abnamro.recipe.dto.request.RecipeRequestDTO;
import com.abnamro.recipe.dto.response.RecipeResponseDTO;
import com.abnamro.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Tag(name = "Recipes", description = "The recipes API")
@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping(value = "/add")
    @ApiResponse(responseCode = "201")
    @Operation(description = "Rest method used to store the requested recipe and return all previously saved recipes!")
    public ResponseEntity<RecipeResponseDTO> createRecipe(@RequestBody @Valid final RecipeDTO recipeDTO) {
        return new ResponseEntity<>(recipeService.create(recipeDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    @ApiResponse(responseCode = "200")
    @Operation(description = "Rest method used to update the requested recipe and return all previously saved recipes!")
    public ResponseEntity<RecipeResponseDTO> updateRecipe(@RequestBody @Valid final RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeService.update(recipeDTO));
    }

    @GetMapping("/{id}")
    @Operation(description = "Rest method used to get a specific recipe details by id!")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable final Long id) {
        return ResponseEntity.ok(recipeService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    @Operation(description = "Rest method used to delete specific recipe")
    public ResponseEntity<Void> deleteRecipe(@PathVariable final Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(description = "Rest method used to retrieve all recipes based on the mentioned criteria")
    public ResponseEntity<List<RecipeDTO>> search(
            @RequestParam(name = "vegetarian", required = false) @Parameter(description = "If present will search " +
                    "for vegetarian recipe only") Boolean vegetarian,
            @RequestParam(name = "servings", required = false) @Parameter(description = "If present will search " +
                    "recipe with specific servings") Integer servings,
            @RequestParam(name = "includeIngredients", required = false) @Parameter(description = "If present will search " +
                    "recipes that include certain ingredients") List<String> includeIngredients,
            @RequestParam(name = "excludeIngredients", required = false) @Parameter(description = "If present will search " +
                    "recipes that exclude certain ingredients") List<String> excludeIngredients,
            @RequestParam(name = "searchKey", required = false) @Parameter(description = "If present will search " +
                    "recipes that has the search key within the instructions") String searchKey) {
        return ResponseEntity.ok(recipeService.findAll(RecipeRequestDTO.builder()
                .vegetarian(vegetarian)
                .servings(servings)
                .searchKey(searchKey)
                .excludeIngredients(excludeIngredients)
                .includeIngredients(includeIngredients)
                .build()));
    }

}
