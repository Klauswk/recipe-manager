package com.github.klauswk.recipemanager.controller.mapper;

import org.mapstruct.Mapper;

import com.github.klauswk.recipemanager.controller.representation.RecipeFilterRepresentation;
import com.github.klauswk.recipemanager.domain.RecipeFilter;

@Mapper(componentModel = "spring")
public interface RecipeFilterMapper {
    RecipeFilter toDomain(RecipeFilterRepresentation ingredient);
}
