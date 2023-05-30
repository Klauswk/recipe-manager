package com.github.klauswk.recipemanager.exception;

public class RecipeAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7506209893553458827L;

	public RecipeAlreadyExistsException(String recipeName) {
		super(String.format("The recipe %s already exists.", recipeName));
	}

}
