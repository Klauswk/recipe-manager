package com.github.klauswk.recipemanager.domain;

import java.util.List;

public interface RecipeRepository {

	public Recipe addRecipe(Recipe recipe);

	public Recipe updateRecipe(Recipe recipe);

	public Recipe removeRecipe(Recipe recipe);

	public List<Recipe> fetchRecipe(RecipeFilter recipe);

}
