package com.github.klauswk.recipemanager.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	
	public Recipe addRecipe(Recipe recipe) {
		return recipeRepository.addRecipe(recipe);
	}

	public Recipe updateRecipe(Recipe recipe) {
		return recipeRepository.updateRecipe(recipe);
	}

	public Recipe removeRecipe(Recipe recipe) {
		return recipeRepository.removeRecipe(recipe);
	}

	public List<Recipe> fetchRecipe(RecipeFilter recipeFilter) {
		return recipeRepository.fetchRecipe(recipeFilter);
	}
	
}
