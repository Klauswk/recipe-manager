package com.github.klauswk.recipemanager.controller.representation;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeRepresentation {

	@Nullable
	private Long id;

	@NotBlank
	@Size(min=3, max=250)
	private String name;

	@NotNull
	private Boolean vegetarian;

	@NotEmpty
	@Size(min=1, max=10)
	private List<IngredientRepresentation> ingredients;

	@NotNull
	@Min(1)
	@Max(10)
	private Integer numberOfServings;

	@NotBlank
	@Size(min=3, max=1000)
	private String instructions;
	
}
