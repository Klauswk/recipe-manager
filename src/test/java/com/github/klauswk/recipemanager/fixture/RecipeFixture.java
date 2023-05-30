package com.github.klauswk.recipemanager.fixture;

import java.util.Arrays;

import com.github.klauswk.recipemanager.domain.Recipe;

public class RecipeFixture {

	public static Recipe omnivorousRecipe() {
		return Recipe.builder().name("Smash potato").numberOfServings(2).vegetarian(false)
				.ingredients(
						Arrays.asList(IngredientFixture.milk(), IngredientFixture.butter(), IngredientFixture.potato()))
				.build();
	}

	public static Recipe strictVegetarianRecipe() {
		return Recipe
				.builder().name("Sunomono").numberOfServings(1).vegetarian(true).ingredients(Arrays
						.asList(IngredientFixture.cucumber(), IngredientFixture.salt(), IngredientFixture.vinegar()))
				.build();
	}

}
