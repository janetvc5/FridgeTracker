package org.springframework.fridgetracker.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {
	private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
	
	@RequestMapping(method= RequestMethod.GET, path="/recipes")
	public List<Recipe> getAllRecipes() {
		//Returning this for mockito test
		return new ArrayList();
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/recipes/{id}")
	public Optional<Recipe> getRecipeById(@PathParam("id") Integer id) {
		// return this for mockito
		return Optional.empty();
	}
}
