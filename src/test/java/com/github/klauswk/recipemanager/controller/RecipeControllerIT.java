package com.github.klauswk.recipemanager.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.klauswk.recipemanager.Application;
import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;
import com.github.klauswk.recipemanager.fixture.RecipeRepresentationFixture;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class RecipeControllerIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenRecipeWhenInsertShouldReturn200() throws Exception {
		
		//Given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();
		
		//When
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Smash potato")))
				.andReturn()
				.getResponse()
				.getContentAsString();

		//Clean up
		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);
				
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(insertRecipe)))
				.andExpect(status().isOk());
	}

	@Test
	public void givenRecipeWhenInsertTwiceShouldReturn406() throws Exception {
		
		//Given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();
		
		//When
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Smash potato")))
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		mvc.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(recipe)))
			.andExpect(status().isNotAcceptable())
			.andExpect(jsonPath("$.message", notNullValue()))
			.andExpect(jsonPath("$.code", is("406")));
		
		//Clean up
		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);
				
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(insertRecipe)))
				.andExpect(status().isOk());
	}

	@Test
	public void givenRecipeWhenGetShouldBeSameAsInsert() throws Exception {
		
		//given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();

		//when
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Smash potato"))).andReturn().getResponse()
				.getContentAsString();

		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);

		mvc.perform(get("/v1/recipe").contentType(MediaType.APPLICATION_JSON).param("name", "smash potato"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is(insertRecipe.getName())))
				.andExpect(jsonPath("$[0].id", is(insertRecipe.getId().intValue())))
				.andExpect(jsonPath("$[0].vegetarian", is(insertRecipe.getVegetarian())))
				.andExpect(jsonPath("$[0].instructions", is(insertRecipe.getInstructions())));
		
		//Clean up
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(insertRecipe)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void givenRecipeWhenPatchShouldBeTheSame() throws Exception {
		
		//given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();

		//when
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Smash potato"))).andReturn().getResponse()
				.getContentAsString();

		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);
		
		RecipeRepresentation updatedRecipe = RecipeRepresentation.builder().name("Smash Potatos")
				.id(insertRecipe.getId())
				.instructions(insertRecipe.getInstructions())
				.numberOfServings(insertRecipe.getNumberOfServings())
				.vegetarian(true)
				.ingredients(insertRecipe.getIngredients())
				.build();
		
		mvc.perform(put("/v1/recipe").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedRecipe)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(updatedRecipe.getName())))
				.andExpect(jsonPath("$.id", is(updatedRecipe.getId().intValue())))
				.andExpect(jsonPath("$.vegetarian", is(updatedRecipe.getVegetarian())))
				.andExpect(jsonPath("$.numberOfServings", is(updatedRecipe.getNumberOfServings())))
				.andExpect(jsonPath("$.instructions", is(updatedRecipe.getInstructions())));
		
		//Clean up
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedRecipe)))
				.andExpect(status().isOk());
	}

	@Test
	public void givenRecipeWhenUpdateNotExistentShouldReturn404() throws Exception {
		
		//Given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();
		recipe.setId(10l);
		
		//When		
		mvc.perform(put("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(recipe)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message", notNullValue()))
			.andExpect(jsonPath("$.code", is("404")));
	}
	
	@Test
	public void givenRecipeWhenDeleteShouldBeTheSameInserted() throws Exception {
		
		//given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();

		//when
		String jsonResult = mvc
				.perform(post("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(recipe)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Smash potato"))).andReturn().getResponse()
				.getContentAsString();

		RecipeRepresentation insertRecipe = objectMapper.readValue(jsonResult, RecipeRepresentation.class);
		
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(insertRecipe)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(insertRecipe.getName())))
				.andExpect(jsonPath("$.id", is(insertRecipe.getId().intValue())))
				.andExpect(jsonPath("$.vegetarian", is(insertRecipe.getVegetarian())))
				.andExpect(jsonPath("$.numberOfServings", is(insertRecipe.getNumberOfServings())))
				.andExpect(jsonPath("$.instructions", is(insertRecipe.getInstructions())));
	}

	@Test
	public void givenRecipeWhenDeleteNotExistentShouldReturn404() throws Exception {
		
		//Given
		RecipeRepresentation recipe = RecipeRepresentationFixture.omnivorousRecipeRepresentation();
		recipe.setId(10l);
		
		//When		
		mvc.perform(delete("/v1/recipe").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(recipe)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message", notNullValue()))
			.andExpect(jsonPath("$.code", is("404")));
	}
}