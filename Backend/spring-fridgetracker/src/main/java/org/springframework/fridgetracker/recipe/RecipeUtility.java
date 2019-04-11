package org.springframework.fridgetracker.recipe;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.fridgetracker.recipeitem.Recipeitem;

public class RecipeUtility {
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
