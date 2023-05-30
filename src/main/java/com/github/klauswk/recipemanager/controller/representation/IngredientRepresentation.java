package com.github.klauswk.recipemanager.controller.representation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientRepresentation {

	@NotBlank
	@Size(min=3, max=250)
	private String name;
	
}
