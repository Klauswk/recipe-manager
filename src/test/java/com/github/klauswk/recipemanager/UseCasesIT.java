package com.github.klauswk.recipemanager;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.klauswk.recipemanager.controller.representation.IngredientRepresentation;
import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;
import com.github.klauswk.recipemanager.fixture.IngredientRepresentationFixture;
import com.github.klauswk.recipemanager.fixture.RecipeRepresentationFixture;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class UseCasesIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenRecipeWhenFilterByVegetarianShouldReturnVegetarianOnly() throws Exception {
		
		//Given
		RecipeRepresentation recipe = RecipeRepresentationFixture.strictVegetarianRecipeRepresentation();

		//when
		//non vegetarian
		 String nonVegetarianString = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(RecipeRepresentationFixture.omnivorousRecipeRepresentation())))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);


		mvc.perform(get("/v1/recipe").contentType(MediaType.APPLICATION_JSON).param("vegetarian", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].vegetarian", is(true)));
		
		//Clean up
		RecipeRepresentation nonVegetarian = objectMapper.readValue(nonVegetarianString, RecipeRepresentation.class);
		
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nonVegetarian)))
				.andExpect(status().isOk());

		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(insertRecipe)))
				.andExpect(status().isOk());
	}

	@Test
	public void givenRecipeWhenFilteringByIngredientPotatoAndServing4ShoulReturnCorrectResult() throws Exception {
		
		//Given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();

		//when
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andReturn()
				.getResponse()
				.getContentAsString();

		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);

		mvc.perform(get("/v1/recipe").contentType(MediaType.APPLICATION_JSON).param("numberOfServings", "4").param("includes", "potato"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is(insertRecipe.getName())))
				.andExpect(jsonPath("$[0].numberOfServings", is(4)))
				.andExpect(jsonPath("$[0].ingredients", hasSize(3)));
		
		//Clean up
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(insertRecipe)))
				.andExpect(status().isOk());
	}

	@Test
	public void givenRecipeWhenFilteringByInstructionAndExcludesShoulReturnCorrectResult() throws Exception {
		
		//Given
		RecipeRepresentation recipeUsingOvenAndSalmonRepresentation = RecipeRepresentation.builder()
				.name("Salmon and Oven")
				.instructions("This should be done in the oven")
				.ingredients(Arrays.asList(IngredientRepresentation.builder()
						.name("Salmon")
						.build()))
				.numberOfServings(4)
				.vegetarian(false)
				.build();

		//Given
		RecipeRepresentation recipeUsingOvenAndPotatoRepresentation = RecipeRepresentation.builder()
				.name("Potato and Oven")
				.instructions("Cooking potato in the oven is almost french fries")
				.ingredients(Arrays.asList(IngredientRepresentationFixture.potato()))
				.numberOfServings(4)
				.vegetarian(true)
				.build();

		//when
		String recipeUsingOvenAndSalmonString = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipeUsingOvenAndSalmonRepresentation)))
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		String recipeUsingOvenAndPotatoString = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipeUsingOvenAndPotatoRepresentation)))
				.andReturn()
				.getResponse()
				.getContentAsString();

		RecipeRepresentation recipeUsingOvenAndPotato = objectMapper.readValue(recipeUsingOvenAndPotatoString, RecipeRepresentation.class);

		mvc.perform(get("/v1/recipe").contentType(MediaType.APPLICATION_JSON).param("excludes", "salmon").param("instructions", "Oven"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(recipeUsingOvenAndPotato.getName())))
				.andExpect(jsonPath("$[0].instructions", is(recipeUsingOvenAndPotato.getInstructions())));
		
		//Clean up
		RecipeRepresentation recipeUsingOvenAndSalmon = objectMapper.readValue(recipeUsingOvenAndSalmonString, RecipeRepresentation.class);
		
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(recipeUsingOvenAndSalmon)))
				.andExpect(status().isOk());
		
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(recipeUsingOvenAndPotato)))
				.andExpect(status().isOk());
	}
}
