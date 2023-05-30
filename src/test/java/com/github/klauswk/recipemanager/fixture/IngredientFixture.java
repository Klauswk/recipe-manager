package com.github.klauswk.recipemanager.fixture;

import com.github.klauswk.recipemanager.domain.Ingredient;

public class IngredientFixture {
	
	public static Ingredient potato() {
		return Ingredient.builder()
				.name("Potato")
				.build();
	}

	public static Ingredient milk() {
		return Ingredient.builder()
				.name("Milk")
				.build();
	}

	public static Ingredient butter() {
		return Ingredient.builder()
				.name("Butter")
				.build();
	}

	public static Ingredient cucumber() {
		return Ingredient.builder()
				.name("Cucumber")
				.build();
	}

	public static Ingredient vinegar() {
		return Ingredient.builder()
				.name("Vinegar")
				.build();
	}

	public static Ingredient salt() {
		return Ingredient.builder()
				.name("Salt")
				.build();
	}
}
