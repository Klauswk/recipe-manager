package com.github.klauswk.recipemanager.database.persistent;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipePO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "vegetarian")
	private Boolean vegetarian;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "recipe", 
    	    orphanRemoval = true)
	private List<IngredientPO> ingredients;

	@Column(name = "numberOfServings")
	private Integer numberOfServings;

	@Column(name = "instructions", length = 1000)
	private String instructions;
}
