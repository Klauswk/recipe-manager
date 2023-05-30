package com.github.klauswk.recipemanager.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recipe {

	private Long id;
	private String name;
	private Boolean vegetarian;
	private List<Ingredient> ingredients;
	private Integer numberOfServings;
	private String instructions;
	
}
