package com.github.klauswk.recipemanager.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.klauswk.recipemanager.database.persistent.IngredientPO;
import com.github.klauswk.recipemanager.database.persistent.RecipePO;
import com.github.klauswk.recipemanager.domain.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientPOMapper {
    Ingredient toDomain(IngredientPO ingredient);
    
    @Mapping(source = "ingredient.name", target = "name")
    @Mapping(source = "recipe", target = "recipe")
    IngredientPO toPersistentObject(Ingredient ingredient, RecipePO recipe);
    
}
