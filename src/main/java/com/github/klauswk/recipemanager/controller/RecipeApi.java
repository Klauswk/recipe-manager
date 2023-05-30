package com.github.klauswk.recipemanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.github.klauswk.recipemanager.controller.representation.RecipeFilterRepresentation;
import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface RecipeApi {
	
	@Operation(summary = "Find the list of recipes base on the filter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of recipes based on the filter")})
	public List<RecipeRepresentation> findAllRecipe(RecipeFilterRepresentation recipeFilter);

	@Operation(summary = "Delete the recipe resource", requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {@ExampleObject(summary = "Add a new recipe" ,value = "{"
			+ "    \"id\": 1,"
			+ "    \"name\": \"Smash Potatos\","
			+ "    \"vegetarian\": true,"
			+ "    \"ingredients\": ["
			+ "        {"
			+ "            \"name\": \"Potato\""
			+ "        }"
			+ "    ],"
			+ "    \"numberOfServings\": 2,"
			+ "    \"instructions\": \"This contains some instructions\""
			+ "}"),
	})))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the deleted resource", content = @Content),
			@ApiResponse(responseCode = "404", description = "Resource not found")})
	public RecipeRepresentation deleteRecipe(@RequestBody RecipeRepresentation representation);

	@Operation(summary = "Update the recipe resource fully", requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {@ExampleObject(summary = "Add a new recipe" ,value = "{"
			+ "    \"id\": 1,"
			+ "    \"name\": \"Smash Potatos\","
			+ "    \"vegetarian\": true,"
			+ "    \"ingredients\": ["
			+ "        {"
			+ "            \"name\": \"Potato\""
			+ "        }"
			+ "    ],"
			+ "    \"numberOfServings\": 2,"
			+ "    \"instructions\": \"This contains some instructions\""
			+ "}"),
	})))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the updated resource", content = @Content),
			@ApiResponse(responseCode = "404", description = "Resource not found")})
	public RecipeRepresentation updateRecipe(@RequestBody RecipeRepresentation representation);

	@Operation(summary = "Add the recipe resource", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {@ExampleObject(summary = "Add a new recipe" ,value = "{"
					+ "    \"name\": \"Smash Potato\","
					+ "    \"vegetarian\": false,"
					+ "    \"ingredients\": ["
					+ "        {"
					+ "            \"name\": \"Potato\""
					+ "        },"
					+ "        {"
					+ "            \"name\": \"Milk\""
					+ "        }"
					+ "    ],"
					+ "    \"numberOfServings\": 2,"
					+ "    \"instructions\": \"This contains some instructions\""
					+ "}"),
			})))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the inserted resource", content = @Content),
			@ApiResponse(responseCode = "406", description = "Resource already exists")})
	public RecipeRepresentation addRecipe(@RequestBody RecipeRepresentation representation);
}
