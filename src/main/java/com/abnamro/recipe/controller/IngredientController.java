package com.abnamro.recipe.controller;

import com.abnamro.recipe.dto.IngredientDTO;
import com.abnamro.recipe.dto.request.IngredientRequestDTO;
import com.abnamro.recipe.service.IngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Tag(name = "Ingredients", description = "The ingredients API")
@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping(value = "/prepare")
    public ResponseEntity<List<IngredientDTO>> createRecipe(@RequestBody @Valid final IngredientRequestDTO request) {
        return ResponseEntity.ok(ingredientService.prepare(request));
    }
}
