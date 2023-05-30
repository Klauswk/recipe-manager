package com.github.klauswk.recipemanager.controller.representation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeFilterRepresentation {

	@Size(min=3, max=250)
	private String name;

	@Size(min=3, max=250)
	private String includes;

	@Size(min=3, max=250)
	private String excludes;

	private Boolean vegetarian;

	@Min(1)
	@Max(10)
	private Integer numberOfServings;

	@Size(min=3, max=1000)
	private String instructions;
	
}
