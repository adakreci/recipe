package com.abnamro.recipe.integration.controller;

import com.abnamro.recipe.RecipeApplication;
import com.abnamro.recipe.controller.RecipeController;
import com.abnamro.recipe.controller.error.RestExceptionHandler;
import com.abnamro.recipe.domain.Ingredient;
import com.abnamro.recipe.domain.Recipe;
import com.abnamro.recipe.dto.IngredientDTO;
import com.abnamro.recipe.dto.RecipeDTO;
import com.abnamro.recipe.dto.enumeration.MeasureUnit;
import com.abnamro.recipe.dto.request.RecipeRequestDTO;
import com.abnamro.recipe.dto.response.RecipeResponseDTO;
import com.abnamro.recipe.mapper.RecipeMapper;
import com.abnamro.recipe.repository.IngredientRepository;
import com.abnamro.recipe.repository.RecipeRepository;
import com.abnamro.recipe.service.RecipeService;
import com.abnamro.recipe.service.RecipeSpecificationQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeSpecificationQueryService recipeSpecificationQueryService;

    @Test
    public void testCreateRecipe() throws Exception {
        // Prepare request body
        var recipeDTO = RecipeDTO.builder()
                                       .name("Test Recipe")
                                       .instructions("Test Instructions")
                                       .servings(4)
                                       .vegetarian(false)
                                       .ingredients(new HashSet<>())
                                       .build();

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/recipes/add")
                                              .content(objectMapper.writeValueAsString(recipeDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").exists())
               .andExpect(MockMvcResultMatchers.jsonPath("$.recipes[0].name").value("Test Recipe"));
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        // Prepare request body
        var recipeDTO = RecipeDTO.builder()
                                       .recipeId(1L)
                                       .name("Updated Recipe")
                                       .instructions("Updated Instructions")
                                       .servings(2)
                                       .vegetarian(true)
                                       .ingredients(new HashSet<>())
                                       .build();

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/update")
                                              .content(objectMapper.writeValueAsString(recipeDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").exists())
               .andExpect(MockMvcResultMatchers.jsonPath("$.recipes[0].name").value("Updated Recipe"));
    }

//    @Test
//    public void testGetRecipe() throws Exception {
//        // Prepare test data
//        var recipeId = 1L; // Use the desired recipeId
//        var recipeDTO = RecipeDTO.builder()
//                                       .recipeId(recipeId)
//                                       .name("Test Recipe")
//                                       .instructions("Test Instructions")
//                                       .servings(4)
//                                       .vegetarian(false)
//                                       .ingredients(new HashSet<>())
//                                       .build();
//
//        // Mock the RecipeService
//        when(recipeService.getById(eq(recipeId))).thenReturn(recipeDTO);
//
//        // Perform GET request to retrieve a recipe by recipeId
//        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/{recipeId}", recipeId))
//               .andExpect(MockMvcResultMatchers.status().isOk())
//               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(recipeId))
//               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Recipe"));
//
//        // Verify that RecipeService.getById was called with the correct recipeId
//        verify(recipeService).getById(eq(recipeId));
//    }


    @Test
    public void testDeleteRecipe() throws Exception {
        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/1"))
               .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}