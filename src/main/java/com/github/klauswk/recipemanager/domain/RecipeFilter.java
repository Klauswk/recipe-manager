package com.github.klauswk.recipemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeFilter {
	
	private Long id;
	private String name;
	private String includes;
	private String excludes;
	private Boolean vegetarian;
	private Integer numberOfServings;
	private String instructions;
	
}
