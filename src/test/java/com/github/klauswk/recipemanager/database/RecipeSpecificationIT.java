package com.github.klauswk.recipemanager.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.klauswk.recipemanager.Application;
import com.github.klauswk.recipemanager.database.persistent.RecipePO;
import com.github.klauswk.recipemanager.domain.RecipeFilter;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class RecipeSpecificationIT {

	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void givenNoFilterWhenCallingShouldReturnSingleFilter() {
		// given
		RecipeSpecification specification = new RecipeSpecification(new RecipeFilter());

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipePO> cq = cb.createQuery(RecipePO.class);
		Root<RecipePO>  root = cq.from(RecipePO.class);
		
		//when
		Predicate predicate = specification.toPredicate(root, cq, cb);
		
		assertEquals(1, predicate.getExpressions().size());
	}
	
	@Test
	public void givenNameFilterWhenCallingShouldReturnTwoFilter() {
		// given
		RecipeFilter recipeFilter = RecipeFilter.builder()
				.name("name")
				.build();
		
		RecipeSpecification specification = new RecipeSpecification(recipeFilter);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipePO> cq = cb.createQuery(RecipePO.class);
		Root<RecipePO>  root = cq.from(RecipePO.class);
		
		//when
		Predicate predicate = specification.toPredicate(root, cq, cb);
		
		assertEquals(2, predicate.getExpressions().size());
	}

	@Test
	public void givenExcludesFilterWhenCallingShouldReturnTwoFilter() {
		// given
		RecipeFilter recipeFilter = RecipeFilter.builder()
				.excludes("excludes")
				.build();
		
		RecipeSpecification specification = new RecipeSpecification(recipeFilter);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipePO> cq = cb.createQuery(RecipePO.class);
		Root<RecipePO>  root = cq.from(RecipePO.class);
		
		//when
		Predicate predicate = specification.toPredicate(root, cq, cb);
		
		assertEquals(2, predicate.getExpressions().size());
	}

	@Test
	public void givenIncludesFilterWhenCallingShouldReturnTwoFilter() {
		// given
		RecipeFilter recipeFilter = RecipeFilter.builder()
				.includes("includes")
				.build();
		
		RecipeSpecification specification = new RecipeSpecification(recipeFilter);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipePO> cq = cb.createQuery(RecipePO.class);
		Root<RecipePO>  root = cq.from(RecipePO.class);
		
		//when
		Predicate predicate = specification.toPredicate(root, cq, cb);
		
		assertEquals(2, predicate.getExpressions().size());
	}

	@Test
	public void givenInstructionsFilterWhenCallingShouldReturnTwoFilter() {
		// given
		RecipeFilter recipeFilter = RecipeFilter.builder()
				.instructions("instructions")
				.build();
		
		RecipeSpecification specification = new RecipeSpecification(recipeFilter);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipePO> cq = cb.createQuery(RecipePO.class);
		Root<RecipePO>  root = cq.from(RecipePO.class);
		
		//when
		Predicate predicate = specification.toPredicate(root, cq, cb);
		
		assertEquals(2, predicate.getExpressions().size());
	}

	@Test
	public void givenVegetarianAndNumberOfServingsFilterWhenCallingShouldReturnThreeFilter() {
		// given
		RecipeFilter recipeFilter = RecipeFilter.builder()
				.vegetarian(true)
				.numberOfServings(2)
				.build();
		
		RecipeSpecification specification = new RecipeSpecification(recipeFilter);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipePO> cq = cb.createQuery(RecipePO.class);
		Root<RecipePO>  root = cq.from(RecipePO.class);
		
		//when
		Predicate predicate = specification.toPredicate(root, cq, cb);
		
		assertEquals(3, predicate.getExpressions().size());
	}
}
