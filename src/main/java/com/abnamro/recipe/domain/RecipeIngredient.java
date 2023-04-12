package com.abnamro.recipe.domain;

import com.abnamro.recipe.domain.enumeration.IngredientUnit;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RecipeIngredient {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredientId", referencedColumnName = "ingredientId")
    private Ingredient ingredient;

    @Column
    private Double measure;

    @Column
    @Enumerated(EnumType.STRING)
    private IngredientUnit unit;

}
