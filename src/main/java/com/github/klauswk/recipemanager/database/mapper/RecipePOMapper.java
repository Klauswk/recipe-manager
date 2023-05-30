package com.github.klauswk.recipemanager.database.mapper;

import org.mapstruct.Mapper;

import com.github.klauswk.recipemanager.database.persistent.RecipePO;
import com.github.klauswk.recipemanager.domain.Recipe;

@Mapper(componentModel = "spring")
public interface RecipePOMapper {
    Recipe toDomain(RecipePO recipe);
    
    RecipePO toPersistentObject(Recipe recipe);
}
