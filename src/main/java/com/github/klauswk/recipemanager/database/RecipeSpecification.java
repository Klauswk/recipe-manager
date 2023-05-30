package com.github.klauswk.recipemanager.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.github.klauswk.recipemanager.database.persistent.IngredientPO;
import com.github.klauswk.recipemanager.database.persistent.RecipePO;
import com.github.klauswk.recipemanager.domain.RecipeFilter;

public class RecipeSpecification implements Specification<RecipePO> {

	private static final long serialVersionUID = -8532618551965267079L;

	private final RecipeFilter recipeFilter;

	public RecipeSpecification(RecipeFilter recipeFilter) {
		this.recipeFilter = recipeFilter;
	}

	@Override
	public Predicate toPredicate(Root<RecipePO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE));

		if (recipeFilter.getId() != null) {
			predicates.add(cb.equal(cb.upper(root.get("id")), recipeFilter.getId()));
		}
		
		if (recipeFilter.getName() != null) {
			predicates.add(cb.like(cb.upper(root.get("name")), "%" + recipeFilter.getName().toUpperCase() + "%"));
		}

		if (recipeFilter.getInstructions() != null) {
			predicates.add(cb.like(cb.upper(root.get("instructions")),
					"%" + recipeFilter.getInstructions().toUpperCase() + "%"));
		}

		if (recipeFilter.getVegetarian() != null) {
			predicates.add(cb.equal(root.get("vegetarian"), recipeFilter.getVegetarian()));
		}

		if (recipeFilter.getNumberOfServings() != null) {
			predicates.add(cb.equal(root.get("numberOfServings"), recipeFilter.getNumberOfServings()));
		}

		if (recipeFilter.getIncludes() != null) {
			Subquery<IngredientPO> subquery = query.subquery(IngredientPO.class);
			
			Root<IngredientPO> subqueryRoot = subquery.from(IngredientPO.class);
			
			subquery.select(subqueryRoot.get("recipe").get("id"))
			.where(cb.like(cb.upper(subqueryRoot.get("name")), "%" + recipeFilter.getIncludes().toUpperCase() + "%"));
			
			predicates.add(root.get("id").in(subquery));
		}

		if (recipeFilter.getExcludes() != null) {
			Subquery<IngredientPO> subquery = query.subquery(IngredientPO.class);
			
			Root<IngredientPO> subqueryRoot = subquery.from(IngredientPO.class);
			
			subquery.select(subqueryRoot.get("recipe").get("id"))
			.where(cb.like(cb.upper(subqueryRoot.get("name")), "%" + recipeFilter.getExcludes().toUpperCase() + "%"));
			
			predicates.add(cb.not(root.get("id").in(subquery)));
		}

		return cb.and(predicates.toArray(new Predicate[0]));
	}

}