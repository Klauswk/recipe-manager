package com.github.klauswk.recipemanager.exception;

public class RecipeNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 7506209893843458827L;

	public RecipeNotFoundException(String recipeName) {
		super(String.format("The recipe %s was not found.",recipeName));
	}
	
}
