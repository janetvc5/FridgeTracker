package org.springframework.fridgetracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
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
public class FridgeTrackerMockitoTests {
	
	@Test
	public void AddingItem() {
		List<Item> results = new ArrayList<Item>();
		Item redApple = new Item(); 
		redApple.setId(50); redApple.setItemname("Red Apple"); redApple.setTags("Fruit");
		results.add(redApple);
		/*Mocking of API */ 
		ItemController itemController = mock(ItemController.class);
		when(itemController.getItemByName("Apple")).thenReturn(results);
		/* Front End Handling of Adding Item */
		Fridgecontents fridgecontent = new Fridgecontents();
		fridgecontent.setFoodname(itemController.getItemByName("Apple").get(0).getItemname()); 
		fridgecontent.setId(itemController.getItemByName("Apple").get(0).getId());
		fridgecontent.setQuantity(1);
		FridgecontentsController controller = mock(FridgecontentsController.class);
		HashMap<String,String> map = new HashMap<>();
		map.put("fridge item added","true");
		when(controller.saveFridgecontents(fridgecontent)).thenReturn(map);
		assertTrue(controller.saveFridgecontents(fridgecontent).toString().contains("true"));
	}
	
}
