package com.github.klauswk.recipemanager.fixture;


import java.util.Arrays;

import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;

public class RecipeRepresentationFixture {
	
	public static RecipeRepresentation omnivorousRecipeRepresentation() {
		return RecipeRepresentation.builder()
				.name("Smash potato")
				.numberOfServings(4)
				.vegetarian(false)
				.ingredients(Arrays.asList(IngredientRepresentationFixture.milk(), IngredientRepresentationFixture.butter(), IngredientRepresentationFixture.potato()))
				.instructions("Some instructions")
				.build();
	}

	public static RecipeRepresentation strictVegetarianRecipeRepresentation() {
		return RecipeRepresentation.builder()
				.name("Sunomono")
				.numberOfServings(1)
				.vegetarian(true)
				.ingredients(Arrays.asList(IngredientRepresentationFixture.cucumber(), IngredientRepresentationFixture.salt(), IngredientRepresentationFixture.vinegar()))
				.instructions("Some instructions")
				.build();
	}
	
}
