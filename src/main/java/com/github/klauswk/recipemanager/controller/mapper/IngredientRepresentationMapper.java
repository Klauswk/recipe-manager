package com.github.klauswk.recipemanager.controller.mapper;

import org.mapstruct.Mapper;

import com.github.klauswk.recipemanager.controller.representation.IngredientRepresentation;
import com.github.klauswk.recipemanager.domain.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientRepresentationMapper {
    Ingredient toDomain(IngredientRepresentation ingredient);
    IngredientRepresentation toRepresentation(Ingredient recipe);
}
