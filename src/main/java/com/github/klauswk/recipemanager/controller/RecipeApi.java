package com.github.klauswk.recipemanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.github.klauswk.recipemanager.controller.representation.RecipeFilterRepresentation;
import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface RecipeApi {
	
	@Operation(summary = "Find the list of recipes base on the filter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of recipes based on the filter", content = @Content)})
	public List<RecipeRepresentation> findAllRecipe(RecipeFilterRepresentation recipeFilter);

	@Operation(summary = "Delete the recipe resource")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the deleted resource", content = @Content),
			@ApiResponse(responseCode = "404", description = "Resource not found")})
	public RecipeRepresentation deleteRecipe(@RequestBody RecipeRepresentation representation);

	@Operation(summary = "Update the recipe resource fully")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the updated resource", content = @Content),
			@ApiResponse(responseCode = "404", description = "Resource not found")})
	public RecipeRepresentation updateRecipe(@RequestBody RecipeRepresentation representation);

	@Operation(summary = "Add the recipe resouce")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the inserted resource", content = @Content),
			@ApiResponse(responseCode = "406", description = "Resource already exists")})
	public RecipeRepresentation addRecipe(@RequestBody RecipeRepresentation representation);
}
