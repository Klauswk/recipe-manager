package com.github.klauswk.recipemanager.fixture;

import com.github.klauswk.recipemanager.database.persistent.IngredientPO;

public class IngredientPOFixture {
	
	public static IngredientPO potato() {
		return IngredientPO.builder()
				.name("Potato")
				.build();
	}

	public static IngredientPO milk() {
		return IngredientPO.builder()
				.name("Milk")
				.build();
	}

	public static IngredientPO butter() {
		return IngredientPO.builder()
				.name("Butter")
				.build();
	}

	public static IngredientPO cucumber() {
		return IngredientPO.builder()
				.name("Cucumber")
				.build();
	}

	public static IngredientPO vinegar() {
		return IngredientPO.builder()
				.name("Vinegar")
				.build();
	}

	public static IngredientPO salt() {
		return IngredientPO.builder()
				.name("Salt")
				.build();
	}
}