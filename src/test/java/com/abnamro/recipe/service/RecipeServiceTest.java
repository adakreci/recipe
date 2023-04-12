package com.abnamro.recipe.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.abnamro.recipe.domain.Recipe;
import com.abnamro.recipe.dto.RecipeDTO;
import com.abnamro.recipe.dto.response.RecipeResponseDTO;
import com.abnamro.recipe.mapper.RecipeMapper;
import com.abnamro.recipe.repository.RecipeRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

public class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeValidationService recipeValidationService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private WebMessageResolverService webMessageResolverService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate_ValidRecipe_ReturnsResponseDTO() {
        // Mocking validationService.validateRecipeCreate() to return a blank error message
        when(recipeValidationService.validateRecipeCreate(any(RecipeDTO.class))).thenReturn(StringUtils.EMPTY);

        // Mocking recipeMapper.map() to return a Recipe object
        when(recipeMapper.map(any(RecipeDTO.class))).thenReturn(new Recipe());

        // Mocking webMessageResolverService.getMessage() to return a success message
        when(webMessageResolverService.getMessage(any(String.class))).thenReturn("Success");

        // Call the method to test
        var source = new RecipeDTO();
        var result = recipeService.create(source);

        // Assertions
        assertNotNull(result);
        assertEquals("Success", result.getMessage());
        assertNotNull(result.getRecipes());

        // Verify that validationService.validateRecipeCreate() was called with the correct argument
        verify(recipeValidationService).validateRecipeCreate(eq(source));

        // Verify that recipeMapper.map() was called with the correct argument
        verify(recipeMapper).map(eq(source));

        // Verify that webMessageResolverService.getMessage() was called with the correct argument
        verify(webMessageResolverService).getMessage(eq("recipe.create.success"));

        // Verify that recipeRepository.save() was called once
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }



    @Test
    public void testCreate_InvalidRecipe_ReturnsErrorDTO() {
        // Mocking validationService.validateRecipeCreate() to return an error message
        when(recipeValidationService.validateRecipeCreate(any(RecipeDTO.class))).thenReturn("Invalid recipe");

        // Call the method to test
        var source = new RecipeDTO();
        var result = recipeService.create(source);

        // Assertions
        assertNotNull(result);
        assertEquals("Invalid recipe", result.getMessage());
        assertNull(result.getRecipes());

        // Verify that validationService.validateRecipeCreate() was called with the correct argument
        verify(recipeValidationService).validateRecipeCreate(eq(source));

        // Verify that recipeMapper.map() was not called
        verify(recipeMapper, never()).map(any(RecipeDTO.class));

        // Verify that webMessageResolverService.getMessage() was not called
        verify(webMessageResolverService, never()).getMessage(any(String.class));

        // Verify that recipeRepository.save() was not called
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    void testUpdate_ValidRecipe_ReturnsResponseDTO() {
        // Mocking recipeRepository.findById() to return a Recipe
        var recipe = new Recipe();
        recipe.setName("Test Recipe");
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));

        // Mocking validationService.validateRecipeUpdate() to return null, indicating a valid recipe
        when(recipeValidationService.validateRecipeUpdate(any(Recipe.class), any(RecipeDTO.class))).thenReturn(null);

        // Mocking recipeMapper.map() to return a mapped Recipe object
        var mappedRecipe = new Recipe();
        when(recipeMapper.map(any(Recipe.class), any(RecipeDTO.class))).thenReturn(mappedRecipe);

        // Call the method to test
        var source = new RecipeDTO();
        source.setRecipeId(1L); // Set a valid recipeId for the source
        var result = recipeService.update(source);

        // Assertions
        assertNotNull(result);
        assertNull(result.getMessage());
        assertNotNull(result.getRecipes());

        // Verify that recipeRepository.findById() was called with the correct argument
        verify(recipeRepository).findById(eq(source.getRecipeId()));

        // Verify that validationService.validateRecipeUpdate() was called with the correct arguments
        verify(recipeValidationService).validateRecipeUpdate(eq(recipe), eq(source));

        // Verify that recipeMapper.map() was called with the correct arguments
        verify(recipeMapper).map(eq(recipe), eq(source));

        // Verify that recipeRepository.saveAndFlush() was called with the correct argument
        verify(recipeRepository).saveAndFlush(eq(mappedRecipe));

        // Verify that webMessageResolverService.getMessage() was called once
        verify(webMessageResolverService).getMessage(any(String.class));
    }

    @Test
    void testUpdate_InvalidRecipe_ReturnsErrorDTO() {
        // Mocking recipeRepository.findById() to return a non-empty Optional with a valid Recipe object
        var recipe = new Recipe();
        recipe.setRecipeId(1L);
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));

        // Call the method to test with a source object having a non-null recipeId value
        var source = new RecipeDTO();
        source.setRecipeId(1L); // Set a non-null recipeId for the source
        var result = recipeService.update(source);

        // Assertions
        assertNotNull(result);
        assertEquals(result.getRecipes(), Collections.emptyList() );

        // Verify that recipeRepository.findById() was called with the correct argument
        verify(recipeRepository).findById(eq(source.getRecipeId()));
    }

    @Test
    void testDelete_ExistingRecipe_DeletesRecipe() {
        // Create a mock Recipe object
        var recipe = new Recipe();
        recipe.setRecipeId(1L);

        // Mock the behavior of recipeRepository.findById() to return the mock Recipe object
        when(recipeRepository.findById(eq(1L))).thenReturn(Optional.of(recipe));

        // Call the delete() method with an existing recipe id
        recipeService.delete(1L);

        // Verify that recipeRepository.deleteById() was called with the correct argument
        verify(recipeRepository).deleteById(eq(1L));
    }

    @Test
    void testDelete_NonExistingRecipe_ThrowsResponseStatusException() {
        // Mock the behavior of recipeRepository.findById() to return an empty Optional
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the delete() method with a non-existing recipe id
        assertThrows(ResponseStatusException.class, () -> recipeService.delete(1L));
    }

    @Test
    void testGetById_ExistingRecipe_ReturnsRecipeDTO() {
        // Create a mock Recipe object
        var recipe = new Recipe();
        recipe.setRecipeId(1L);

        // Mock the behavior of recipeRepository.findById() to return the mock Recipe object
        when(recipeRepository.findById(eq(1L))).thenReturn(Optional.of(recipe));

        // Create a mock RecipeDTO object
        var recipeDTO = new RecipeDTO();
        recipeDTO.setRecipeId(1L);

        // Mock the behavior of recipeMapper.map() to return the mock RecipeDTO object
        when(recipeMapper.map(eq(recipe))).thenReturn(recipeDTO);

        // Call the getById() method with an existing recipe id
        var result = recipeService.getById(1L);

        // Verify that recipeRepository.findById() was called with the correct argument
        verify(recipeRepository).findById(eq(1L));

        // Verify that recipeMapper.map() was called with the correct argument
        verify(recipeMapper).map(eq(recipe));

        // Assertions
        assertNotNull(result);
        assertEquals(recipeDTO, result);
    }

    @Test
    void testGetById_NonExistingRecipe_ThrowsResponseStatusException() {
        // Mock the behavior of recipeRepository.findById() to return an empty Optional
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the getById() method with a non-existing recipe id
        assertThrows(ResponseStatusException.class, () -> recipeService.getById(1L));
    }


}
