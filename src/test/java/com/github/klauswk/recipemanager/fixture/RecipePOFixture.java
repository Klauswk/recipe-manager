package com.github.klauswk.recipemanager.fixture;

import java.util.ArrayList;
import java.util.List;

import com.github.klauswk.recipemanager.database.persistent.IngredientPO;
import com.github.klauswk.recipemanager.database.persistent.RecipePO;

public class RecipePOFixture {
	
	public static RecipePO omnivorousRecipe() {
		List<IngredientPO> listIngredients = new ArrayList<>(3);
		listIngredients.add(IngredientPOFixture.milk());
		listIngredients.add(IngredientPOFixture.butter());
		listIngredients.add(IngredientPOFixture.potato());

		return RecipePO.builder()
				.name("Smash potato")
				.numberOfServings(2)
				.vegetarian(false)
				.ingredients(listIngredients)
				.build();
	}

	public static RecipePO strictVegetarianRecipe() {
		List<IngredientPO> listIngredients = new ArrayList<>(3);
		listIngredients.add(IngredientPOFixture.cucumber());
		listIngredients.add(IngredientPOFixture.salt());
		listIngredients.add(IngredientPOFixture.vinegar());
		
		return RecipePO.builder()
				.name("Sunomono")
				.numberOfServings(1)
				.vegetarian(true)
				.ingredients(listIngredients)
				.build();
	}
	
}