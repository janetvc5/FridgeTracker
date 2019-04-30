package org.springframework.fridgetracker.recipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.fridge.Fridge;
import org.springframework.fridgetracker.fridge.FridgeRepository;
import org.springframework.fridgetracker.fridgecontents.Fridgecontents;
import org.springframework.fridgetracker.recipeitem.Recipeitem;
import org.springframework.fridgetracker.recipeitem.RecipeitemRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RecipeController {
	private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	FridgeRepository fridgeRepository;
	@Autowired
	RecipeitemRepository recipeitemRepository;
	
	
	@RequestMapping(method= RequestMethod.GET, path="/recipes")
	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}
	
	/**
	 * Returns all recipes in the database on a GET call to "{url}/recipes"
	 * @return ArrayList of recipes
	 */
	@RequestMapping(method= RequestMethod.POST, path="/recipes/new")
	public Map<String, String> addRecipe(@RequestBody Recipe recipe) {
		Map<String,String> ret = new HashMap<String,String>();
		if(recipe==null) {
			ret.put("success", "false");
			return ret;
		}
		Iterator<LinkedHashMap> i = recipe.getItems().iterator();
		ArrayList<Recipeitem> newList = new ArrayList<Recipeitem>();
		Recipeitem out;
		LinkedHashMap in;
		while(i.hasNext()) {
			in = i.next();
			out = new Recipeitem();
			out.setItemname((String)in.get("itemname"));
			out.setQuantity(((String)in.get("quantity")));
			out.setUnit((String)in.get("unit"));
			newList.add(recipeitemRepository.save(out));
		}
		recipe.setItems(newList);
		recipeRepository.save(recipe);
		ret.put("success", "true");
		return ret;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/recipes/{id}")
	public Optional<Recipe> getRecipeById(@PathParam("id") Integer id) {
		// return this for mockito
		return recipeRepository.findById(id);
	}
	
	/**
	 * Returns a recipe with the given ID on a GET call to "{url}/recipes/{id}"
	 * @param id - The id of the Recipe being retrieved (part of url)
	 * @return An optional - If the recipe exists, it will have a value. Else, it will have nothing
	 */
	@RequestMapping(method=RequestMethod.GET, path="/recipes/pantry/{id}")
	public Map<String,Object> sortedRecipeList(@PathParam("id") Integer id) {
		Map<String,Object> ret = new HashMap<String,Object>();
		Optional<Fridge> f = fridgeRepository.findById(id);
		if(!f.isPresent()) {
			ret.put("success", (Object)"false");
			ret.put("reason", (Object) "fridge does not exist");
		}
		List<Recipe> recipes = recipeRepository.findAll();
		Iterator<Recipe> i = recipes.iterator();
		Recipe r;
		ArrayList<Map<String,Object>> vals = new ArrayList<Map<String,Object>>();
		while(i.hasNext()) {
			r = i.next();
			HashMap<String,Object> newVal = new HashMap<String,Object>();
			newVal.put("recipe", (Object)r);
			newVal.put("rank", (Object) RecipeUtility.percentOfIngredients(r.getItems(),f.get().getFridgecontents()));
			vals.add(newVal);
		}
		Collections.sort(vals, (r1, r2) -> {
			return ((double)r1.get("rank")) < ((double)r2.get("rank")) ? -1
					:((double)r1.get("rank")) > ((double)r2.get("rank")) ? 1
					: 0;
					
		});
		ret.put("recipes", (Object)vals);
		return ret;
	}
}