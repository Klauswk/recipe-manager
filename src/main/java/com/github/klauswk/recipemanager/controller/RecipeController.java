package com.github.klauswk.recipemanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.klauswk.recipemanager.controller.mapper.RecipeFilterMapper;
import com.github.klauswk.recipemanager.controller.mapper.RecipeRepresentationMapper;
import com.github.klauswk.recipemanager.controller.representation.BusinessError;
import com.github.klauswk.recipemanager.controller.representation.RecipeFilterRepresentation;
import com.github.klauswk.recipemanager.controller.representation.RecipeRepresentation;
import com.github.klauswk.recipemanager.domain.RecipeService;
import com.github.klauswk.recipemanager.exception.RecipeAlreadyExistsException;
import com.github.klauswk.recipemanager.exception.RecipeNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RecipeController implements RecipeApi {

	private final RecipeService service;

	private final RecipeRepresentationMapper mapper;

	private final RecipeFilterMapper recipeFilterMapper;

	@GetMapping("/v1/recipe")
	public List<RecipeRepresentation> findAllRecipe(@Nullable @Valid RecipeFilterRepresentation recipeFilter) {
		return service.fetchRecipe(recipeFilterMapper.toDomain(recipeFilter)).stream().map(mapper::toRepresentation)
				.collect(Collectors.toList());
	}

	@DeleteMapping("/v1/recipe")
	public RecipeRepresentation deleteRecipe(@Valid @RequestBody RecipeRepresentation representation) {
		return mapper.toRepresentation(service.removeRecipe(mapper.toDomain(representation)));
	}

	@PutMapping("/v1/recipe")
	public RecipeRepresentation updateRecipe(@Valid @RequestBody RecipeRepresentation representation) {
		return mapper.toRepresentation(service.updateRecipe(mapper.toDomain(representation)));
	}

	@PostMapping("/v1/recipe")
	public RecipeRepresentation addRecipe(@Valid @RequestBody RecipeRepresentation representation) {
		return mapper.toRepresentation(service.addRecipe(mapper.toDomain(representation)));
	}

	@ExceptionHandler(RecipeAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public BusinessError handleRecipeAlreadyExistsException(RecipeAlreadyExistsException ex) {
		return BusinessError.builder()
				.message(ex.getMessage())
				.code("406")
				.build();
	}

	@ExceptionHandler(RecipeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public BusinessError handleRecipeNotFoundException(RecipeNotFoundException ex) {
		return BusinessError.builder()
				.message(ex.getMessage())
				.code("404")
				.build();
	}
}
