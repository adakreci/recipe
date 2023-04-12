package com.abnamro.recipe.service;

import com.abnamro.recipe.domain.Ingredient;
import com.abnamro.recipe.dto.IngredientDTO;
import com.abnamro.recipe.dto.request.IngredientRequestDTO;
import com.abnamro.recipe.mapper.IIngredientMapper;
import com.abnamro.recipe.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IIngredientMapper ingredientMapper;

    public List<IngredientDTO> prepare(final IngredientRequestDTO templateRequest) {
        var ingredients = ingredientRepository.findAll();

        if (CollectionUtils.isEmpty(ingredients)) {
            ingredients = ingredientRepository.saveAll(IntStream.range(1, templateRequest.getLoop() + 1)
                    .mapToObj(operand -> Ingredient.builder()
                            .name("Ingredient " + operand)
                            .healthBenefit("The health benefit " + operand)
                            .build()).collect(Collectors.toList()));
        }

        return ingredientMapper.mapToDTO(ingredients);
    }

}
