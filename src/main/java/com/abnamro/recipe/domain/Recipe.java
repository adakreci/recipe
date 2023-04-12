package com.abnamro.recipe.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdDate;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "longtext")
    private String instructions;

    @Column
    private Integer servings;

    @Column
    private Boolean vegetarian;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipeId", nullable = false)
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    public void setIngredients(Set<RecipeIngredient> ingredients) {
        this.ingredients.clear();
        if (CollectionUtils.isNotEmpty(ingredients)) {
            this.ingredients.addAll(ingredients);
        }
    }
}
