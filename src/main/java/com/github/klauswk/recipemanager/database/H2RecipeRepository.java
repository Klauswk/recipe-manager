package com.github.klauswk.recipemanager.database;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.github.klauswk.recipemanager.database.mapper.IngredientPOMapper;
import com.github.klauswk.recipemanager.database.mapper.RecipePOMapper;
import com.github.klauswk.recipemanager.database.persistent.IngredientPO;
import com.github.klauswk.recipemanager.database.persistent.RecipePO;
import com.github.klauswk.recipemanager.domain.Recipe;
import com.github.klauswk.recipemanager.domain.RecipeFilter;
import com.github.klauswk.recipemanager.domain.RecipeRepository;
import com.github.klauswk.recipemanager.exception.RecipeAlreadyExistsException;
import com.github.klauswk.recipemanager.exception.RecipeNotFoundException;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class H2RecipeRepository implements RecipeRepository {

	private final RecipeDataRepository repository;

	private final RecipePOMapper mapper;

	private final IngredientPOMapper ingredientMapper;

	@Override
	public Recipe addRecipe(Recipe recipe) {
		RecipeFilter recipeFilter = RecipeFilter.builder().name(recipe.getName()).build();

		if (fetchRecipe(recipeFilter).isEmpty()) {
			RecipePO persistentObject = mapper.toPersistentObject(recipe);
			List<IngredientPO> ingredients = recipe.getIngredients()
					.stream()
					.map((i) -> ingredientMapper.toPersistentObject(i, persistentObject))
					.collect(Collectors.toList());
			
			persistentObject.setIngredients(ingredients);
			
			RecipePO savedObject = repository.save(persistentObject);
			
			return mapper.toDomain(savedObject);
		}

		throw new RecipeAlreadyExistsException(recipe.getName());
	}

	@Override
	public Recipe updateRecipe(Recipe recipe) {
		Optional<RecipePO> optionalRecipePO = repository.findById(recipe.getId());

		if (optionalRecipePO.isPresent()) {
			RecipePO persistentObject = optionalRecipePO.get();
			persistentObject.setInstructions(recipe.getInstructions());
			persistentObject.setName(recipe.getName());
			persistentObject.setNumberOfServings(recipe.getNumberOfServings());
			persistentObject.setVegetarian(recipe.getVegetarian());
			persistentObject.getIngredients().clear();
			List<IngredientPO> ingredientsPO = recipe.getIngredients()
					.stream()
					.map((i) -> ingredientMapper.toPersistentObject(i, persistentObject))
					.collect(Collectors.toList());
			persistentObject.getIngredients().addAll(ingredientsPO);
			Recipe updatedRecipe = mapper.toDomain(repository.save(persistentObject));
			return updatedRecipe;
		}

		throw new RecipeNotFoundException(recipe.getName());
	}

	@Override
	public Recipe removeRecipe(Recipe recipe) {
		Optional<RecipePO> optionalRecipePO = repository.findById(recipe.getId());

		if (optionalRecipePO.isPresent()) {
			RecipePO persistentObject = optionalRecipePO.get();
			
			repository.delete(persistentObject);
			return mapper.toDomain(persistentObject);
		}

		throw new RecipeNotFoundException(recipe.getName());
	}

	@Override
	public List<Recipe> fetchRecipe(RecipeFilter recipeFilter) {
		RecipeSpecification specification = new RecipeSpecification(recipeFilter);

		return repository.findAll(specification).stream().map(mapper::toDomain).collect(Collectors.toList());
	}
}
