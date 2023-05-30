package com.github.klauswk.recipemanager.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.klauswk.recipemanager.database.persistent.RecipePO;

public interface RecipeDataRepository extends JpaRepository<RecipePO, Long>, JpaSpecificationExecutor<RecipePO> {

}
