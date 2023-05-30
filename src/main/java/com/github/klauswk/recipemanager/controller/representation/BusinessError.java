package com.github.klauswk.recipemanager.controller.representation;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessError {
	
	@NonNull
	private String message;
	@NonNull
	private String code;
	private String details;
	
}
