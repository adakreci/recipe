package com.abnamro.recipe.service;

import com.abnamro.recipe.domain.Recipe;
import com.abnamro.recipe.dto.RecipeDTO;
import com.abnamro.recipe.dto.request.RecipeRequestDTO;
import com.abnamro.recipe.dto.response.RecipeResponseDTO;
import com.abnamro.recipe.mapper.RecipeMapper;
import com.abnamro.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeValidationService validationService;
    private final RecipeSpecificationQueryService customQuerySpecificationService;
    private final WebMessageResolverService webMessageResolverService;

    public List<RecipeDTO> findAll(final RecipeRequestDTO searchCriteria) {
        return recipeMapper.map(recipeRepository.findAll(customQuerySpecificationService.getSearchQuery(searchCriteria)));
    }

    public RecipeResponseDTO create(final RecipeDTO source) {
        final String errorMessage = validationService.validateRecipeCreate(source);
        if (StringUtils.isNotBlank(errorMessage)) {
            return RecipeResponseDTO.builder()
                    .message(errorMessage)
                    .build();
        }
        recipeRepository.save(recipeMapper.map(source));

        return RecipeResponseDTO.builder()
                .message(webMessageResolverService.getMessage("recipe.create.success"))
                .recipes(findAll())
                .build();
    }

    @Transactional
    public RecipeResponseDTO update(final RecipeDTO source) {
        final Recipe recipe = recipeRepository.findById(source.getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        final String errorMessage = validationService.validateRecipeUpdate(recipe, source);
        if (StringUtils.isNotBlank(errorMessage)) {
            return RecipeResponseDTO.builder()
                    .message(errorMessage)
                    .build();
        }
        recipeRepository.saveAndFlush(recipeMapper.map(recipe, source));
        return RecipeResponseDTO.builder()
                .message(webMessageResolverService.getMessage("recipe.update.success"))
                .recipes(findAll())
                .build();
    }

    public void delete(final Long id) {
        if(recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public RecipeDTO getById(final Long recipeId) {
        return recipeMapper.map(recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    private List<RecipeDTO> findAll() {
        return recipeMapper.map(recipeRepository.findAll());
    }
}
