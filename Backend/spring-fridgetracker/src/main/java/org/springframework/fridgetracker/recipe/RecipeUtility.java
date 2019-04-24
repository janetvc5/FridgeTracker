package org.springframework.fridgetracker.recipe;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.fridgetracker.recipeitem.Recipeitem;

public class RecipeUtility {
	/**
	 * Using a given recipe ID, controller, and ingredient, this function will determine if the given ingredient has the same name as an ingredient in the recipe ingredients
	 * @param con - Recipe Controller used to request Recipeitem list
	 * @param id - ID of the recipe in question
	 * @param ri - Recipe item to be tested for it's presence
	 * @return True if ri is in the ingredients for the recipe, and the recipe exists, false otherwise
	 */
	public static boolean isInRecipe(RecipeController con, Integer id, Recipeitem ri) {
		Optional<Recipe> recO = con.getRecipeById(id);
		if(!recO.isPresent()) return false;
		Recipe rec = recO.get();
		System.out.println("a");
		Iterator<Recipeitem> i = rec.getItems().iterator();
		while(i.hasNext()) {
			if(i.next().getItemname().equals(ri.getItemname())) return true;
		}
		return false;
	}
}
