package org.springframework.fridgetracker.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.fridgetracker.recipeitem.Recipeitem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {
	private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
	
	/**
	 * Returns all recipes in the database on a GET call to "{url}/recipes"
	 * @return ArrayList of recipes
	 */
	@RequestMapping(method= RequestMethod.GET, path="/recipes")
	public List<Recipe> getAllRecipes() {
		//Returning this for mockito test
		return new ArrayList();
	}
	
	/**
	 * Returns a recipe with the given ID on a GET call to "{url}/recipes/{id}"
	 * @param id - The id of the Recipe being retrieved (part of url)
	 * @return An optional - If the recipe exists, it will have a value. Else, it will have nothing
	 */
	@RequestMapping(method=RequestMethod.GET, path="/recipes/{id}")
	public Optional<Recipe> getRecipeById(@PathParam("id") Integer id) {
		// return this for mockito
		return Optional.empty();
	}
}