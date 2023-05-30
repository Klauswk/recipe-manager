package com.github.klauswk.recipemanager.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.klauswk.recipemanager.database.mapper.IngredientPOMapper;
import com.github.klauswk.recipemanager.database.mapper.RecipePOMapper;
import com.github.klauswk.recipemanager.database.persistent.RecipePO;
import com.github.klauswk.recipemanager.domain.Recipe;
import com.github.klauswk.recipemanager.exception.RecipeAlreadyExistsException;
import com.github.klauswk.recipemanager.exception.RecipeNotFoundException;
import com.github.klauswk.recipemanager.fixture.RecipeFixture;
import com.github.klauswk.recipemanager.fixture.RecipePOFixture;

@ExtendWith(MockitoExtension.class)
public class H2RecipeRepositoryTest {

	@InjectMocks
	private H2RecipeRepository repository;

	@Mock
	private RecipeDataRepository dataRepository;
	
	@Mock
	private IngredientPOMapper ingredientMapper;

	@Mock
	private RecipePOMapper mapper;
	
	@Test
	public void givenRecipeWhenAddShouldReturnOk() {
		
		//given
		Recipe omnivorousRecipe = RecipeFixture.omnivorousRecipe();

		RecipePO omnivorousRecipePO = RecipePOFixture.omnivorousRecipe();
		
		Mockito.when(mapper.toPersistentObject(omnivorousRecipe)).thenReturn(omnivorousRecipePO);
		
		Mockito.when(dataRepository.save(omnivorousRecipePO))
		.thenReturn(omnivorousRecipePO);
		
		Mockito.when(mapper.toDomain(omnivorousRecipePO)).thenReturn(omnivorousRecipe);
		
		Recipe newRecipe = repository.addRecipe(omnivorousRecipe);
		
		assertEquals(omnivorousRecipe, newRecipe);
	}

	@Test
	public void givenRecipeWhenAddAndExistsShouldThrowError() {
		//given
		Recipe omnivorousRecipe = RecipeFixture.omnivorousRecipe();

		RecipePO omnivorousRecipePO = RecipePOFixture.omnivorousRecipe();
		
		Mockito.when(dataRepository.findAll(Mockito.any(RecipeSpecification.class)))
		.thenReturn(Arrays.asList(omnivorousRecipePO));
		
		RecipeAlreadyExistsException result = assertThrows(RecipeAlreadyExistsException.class, () -> {
			repository.addRecipe(omnivorousRecipe);
		});
		
		assertEquals("The recipe Smash potato already exists.", result.getMessage());
	}

	@Test
	public void givenRecipeWhenUpdateShouldReturnOk() {

		//given
		Recipe omnivorousRecipe = RecipeFixture.omnivorousRecipe();
		omnivorousRecipe.setId(10l);
		
		RecipePO omnivorousRecipePO = RecipePOFixture.omnivorousRecipe();
		omnivorousRecipePO.setId(10l);
		
		Mockito.when(dataRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(omnivorousRecipePO));

		Mockito.when(dataRepository.save(omnivorousRecipePO))
		.thenReturn(omnivorousRecipePO);
		
		Mockito.when(dataRepository.save(omnivorousRecipePO))
		.thenReturn(omnivorousRecipePO);
		
		Mockito.when(mapper.toDomain(omnivorousRecipePO)).thenReturn(omnivorousRecipe);
		
		Recipe updateRecipe = repository.updateRecipe(omnivorousRecipe);
		
		assertEquals(omnivorousRecipe, updateRecipe);
	}

	@Test
	public void givenRecipeWhenUpdatingAndNotExistsShouldThrowError() {
		//given
		Recipe omnivorousRecipe = RecipeFixture.omnivorousRecipe();
		omnivorousRecipe.setId(10l);
		
		RecipePO omnivorousRecipePO = RecipePOFixture.omnivorousRecipe();
		omnivorousRecipePO.setId(10l);
		
		Mockito.when(dataRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.empty());
		
		RecipeNotFoundException result = assertThrows(RecipeNotFoundException.class, () -> {
			repository.updateRecipe(omnivorousRecipe);
		});
		
		assertEquals("The recipe Smash potato was not found.", result.getMessage());
	}

	@Test
	public void givenRecipeWhenRemovingShouldReturnOk() {

		//given
		Recipe omnivorousRecipe = RecipeFixture.omnivorousRecipe();
		omnivorousRecipe.setId(10l);
		
		RecipePO omnivorousRecipePO = RecipePOFixture.omnivorousRecipe();
		omnivorousRecipePO.setId(10l);
		
		Mockito.when(dataRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(omnivorousRecipePO));

		Mockito.when(mapper.toDomain(omnivorousRecipePO)).thenReturn(omnivorousRecipe);
		
		Recipe updateRecipe = repository.removeRecipe(omnivorousRecipe);
		
		assertEquals(omnivorousRecipe, updateRecipe);
		
		Mockito.verify(dataRepository, Mockito.times(1)).delete(omnivorousRecipePO);
	}
	
	@Test
	public void givenRecipeWhenRemovingAndNotExistsShouldThrowError() {
		//given
		Recipe omnivorousRecipe = RecipeFixture.omnivorousRecipe();
		omnivorousRecipe.setId(10l);
		
		RecipePO omnivorousRecipePO = RecipePOFixture.omnivorousRecipe();
		omnivorousRecipePO.setId(10l);
		
		Mockito.when(dataRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.empty());
		
		RecipeNotFoundException result = assertThrows(RecipeNotFoundException.class, () -> {
			repository.removeRecipe(omnivorousRecipe);
		});
		
		assertEquals("The recipe Smash potato was not found.", result.getMessage());
	}
}
