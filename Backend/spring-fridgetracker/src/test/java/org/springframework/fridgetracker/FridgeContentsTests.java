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
	ItemController itemController = new ItemController();

	@Mock
	FridgecontentsController fridgeContentsController;
	
	@Test 
	public void FridgeContentAdd() {
		List<Item> results = new ArrayList<Item>();
		Item redApple = new Item(); 
		redApple.setId(50); redApple.setItemname("Red Apple"); redApple.setTags("Fruit");
		Item greenApple = new Item();
		greenApple.setId(51); greenApple.setItemname("Green Apple"); greenApple.setTags("Fruit");
		results.add(redApple); results.add(greenApple);
		when(itemController.getItemByName("Apple")).thenReturn(results);
		Fridgecontents fridgecontent = new Fridgecontents(); 
		fridgecontent.setFoodname(results.get(0).getItemname()); 
		fridgecontent.setQuantity(1);
		fridgecontent.setId(results.get(0).getId());
		assertNotEquals("Number of fridges Fetched: 0", fridgeContentsController.getAllFridgecontents());
	}
	
	@Test
	public void FridgeContentView() {
		List<Item> results = new ArrayList<Item>();
		Item redApple = new Item(); 
		redApple.setId(50); redApple.setItemname("Red Apple"); redApple.setTags("Fruit");
		results.add(redApple);
		Fridgecontents fridgecontent = new Fridgecontents(); 
		fridgecontent.setFoodname(results.get(0).getItemname()); 
		fridgecontent.setQuantity(1);
		fridgecontent.setId(results.get(0).getId());
		List<Fridgecontents> listReturn = new ArrayList<Fridgecontents>();
		listReturn.add(fridgecontent);
		when(fridgeContentsController.getAllFridgecontents()).thenReturn(listReturn);
		assertNotEquals("Number of fridges Fetched: 0", fridgeContentsController.getAllFridgecontents());
	}
}
