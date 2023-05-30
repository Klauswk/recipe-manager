package com.github.klauswk.recipemanager.controller.mapper;

import org.mapstruct.Mapper;

import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;
import com.github.klauswk.recipemanager.domain.Recipe;

@Mapper(componentModel = "spring", uses = IngredientRepresentationMapper.class)
public interface RecipeRepresentationMapper {
    Recipe toDomain(RecipeRepresentation recipe);
    RecipeRepresentation toRepresentation(Recipe recipe);
}
