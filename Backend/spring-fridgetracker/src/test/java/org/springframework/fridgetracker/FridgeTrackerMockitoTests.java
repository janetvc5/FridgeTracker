package org.springframework.fridgetracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.fridge.Fridge;
import org.springframework.fridgetracker.fridge.FridgeRepository;
import org.springframework.fridgetracker.fridgecontents.Fridgecontents;
import org.springframework.fridgetracker.fridgecontents.FridgecontentsController;
import org.springframework.fridgetracker.fridgecontents.FridgecontentsRepository;
import org.springframework.fridgetracker.grocerylist.Grocerylist;
import org.springframework.fridgetracker.grocerylist.GrocerylistController;
import org.springframework.fridgetracker.grocerylist.GrocerylistRepository;
import org.springframework.fridgetracker.item.Item;
import org.springframework.fridgetracker.item.ItemController;



@RunWith(MockitoJUnitRunner.class)
public class FridgeTrackerMockitoTests {
	
	@InjectMocks
	GrocerylistController grocerylistController ;
	
	@Mock 
	GrocerylistRepository grocerylistRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	/**
	 * Tests That GroceryItems Can Be Retrieved By Fridge Id
	 */
	@Test
	public void getGrocerylistItemByFridge() {
		Fridge F = new Fridge();
		F.setId(30);
		Grocerylist example = new Grocerylist("Bannana", F , 23, 5);
		Optional<Grocerylist> returnable = Optional.of(example);
		when(grocerylistRepo.findById(30)).thenReturn(returnable);
		
		assertTrue(grocerylistController.getGroceryListByFridge(30).toString().contains("Bannana"));
		assertTrue(grocerylistController.getGroceryListByFridge(30).toString().contains("5"));
		assertTrue(grocerylistController.getGroceryListByFridge(30).toString().contains("23"));
		
	}
	
	
	/**
	 * Tests That Grocery Items Can Be Retrieved By Grocerylist ID
	 */
	@Test
	public void getGroceryListItemByID() {
		Fridge F = new Fridge();
		when(grocerylistRepo.getOne(43)).thenReturn(new Grocerylist("Apple",F,43,2));
		Grocerylist groceryItem = grocerylistRepo.getOne(43);
		
		assertEquals(groceryItem.getFoodname(),"Apple");
		assertEquals(groceryItem.getFridge(),F);
		assertEquals(groceryItem.getId().toString(),"43");
		assertEquals(groceryItem.getQuantity().toString(),"2");
	}
	
	
	/**
	 * Tests That All Grocery Items Can Be Returned
	 */
	@Test
	public void getAllGroceryLists() {
		Fridge F = new Fridge();
		Fridge A = new Fridge();
		List<Grocerylist> list = new ArrayList<Grocerylist>();
		list.add(new Grocerylist("Bannana", F , 9099, 5));
		list.add(new Grocerylist("Apple",F,43,2));
		list.add(new Grocerylist("Carrots", F,2,1));
		list.add(new Grocerylist("Apple", A, 43,5));
		
		when(grocerylistRepo.findAll()).thenReturn(list);
		List<Grocerylist> returned = grocerylistRepo.findAll();
		
		assertEquals(4,returned.size());
		verify(grocerylistRepo, times(1)).findAll();
	}

}
