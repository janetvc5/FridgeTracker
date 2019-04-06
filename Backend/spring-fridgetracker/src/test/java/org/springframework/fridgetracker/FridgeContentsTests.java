package org.springframework.fridgetracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.fridgetracker.fridge.Fridge;
import org.springframework.fridgetracker.fridgecontents.Fridgecontents;
import org.springframework.fridgetracker.fridgecontents.FridgecontentsController;
import org.springframework.fridgetracker.item.Item;
import org.springframework.fridgetracker.item.ItemController;



@RunWith(MockitoJUnitRunner.class)
public class FridgeContentsTests {
	@Mock 
	ItemController itemController;

	@InjectMocks
	FridgecontentsController fridgeContentsController;
	
	@SuppressWarnings("null")
	@Test 
	public void FridgeContentAdd() {
		List<Item> results = new ArrayList<Item>();
		Item redApple = null; redApple.setId(50); redApple.setItemname("Red Apple"); redApple.setTags("Fruit");
		Item greenApple = null; greenApple.setId(51); greenApple.setItemname("Green Apple"); greenApple.setTags("Fruit");
		results.add(redApple); results.add(greenApple);
		when(itemController.getItemByName("Apple")).thenReturn(results);
		Fridgecontents fridgecontent = null; 
		fridgecontent.setFoodname(results.get(0).getItemname()); 
		fridgecontent.setQuantity(1);
		fridgecontent.setId(results.get(0).getId());
		assertNotEquals(null, fridgeContentsController.getAllFridgecontents());
	}
}
