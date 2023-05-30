package com.github.klauswk.recipemanager;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.github.klauswk.recipemanager.domain.Ingredient;
import com.github.klauswk.recipemanager.domain.Recipe;
import com.github.klauswk.recipemanager.domain.RecipeRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Profile({ "local" })
	public CommandLineRunner loadData(RecipeRepository repository) {
		return (args) -> {
			Recipe sunomono = Recipe.builder().name("Sunomono").numberOfServings(1).vegetarian(true)
					.instructions("Slice all the cucumbers very thin and transfer to a medium sized bowl.\n"
							+ "Sprinkle with salt and toss with your hands to ensure even salt coverage to draw out moisture, Wait 5-10 minutes.\n"
							+ "Then rinse the cucumbers thoroughly to remove the salt, drain, then squeeze all the cucumbers to remove excess moisture. The goal is to remove as much water as you can. Don't be afraid to squeeze hard, the cucumbers can take it!\n"
							+ "Mix the rice vinegar, sugar, salt, soy sauce until dissolved, then add to cucumbers.\n"
							+ "Sprinkle with sesame seeds and serve.")
					.ingredients(Arrays.asList(Ingredient.builder().name("Cucumber").build(),
							Ingredient.builder().name("Salt").build(), Ingredient.builder().name("Vinegar").build()))
					.build();

			repository.addRecipe(sunomono);
		};
	}

}
