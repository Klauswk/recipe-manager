package com.github.klauswk.recipemanager.fixture;

import com.github.klauswk.recipemanager.controller.representation.IngredientRepresentation;

public class IngredientRepresentationFixture {

	public static IngredientRepresentation potato() {
		return IngredientRepresentation.builder()
				.name("Potato")
				.build();
	}

	public static IngredientRepresentation milk() {
		return IngredientRepresentation.builder()
				.name("Milk")
				.build();
	}

	public static IngredientRepresentation butter() {
		return IngredientRepresentation.builder()
				.name("Butter")
				.build();
	}

	public static IngredientRepresentation cucumber() {
		return IngredientRepresentation.builder()
				.name("Cucumber")
				.build();
	}

	public static IngredientRepresentation vinegar() {
		return IngredientRepresentation.builder()
				.name("Vinegar")
				.build();
	}

	public static IngredientRepresentation salt() {
		return IngredientRepresentation.builder()
				.name("Salt")
				.build();
	}
}
