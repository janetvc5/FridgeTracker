package org.springframework.fridgetracker.grocerylist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.fridge.Fridge;
import org.springframework.fridgetracker.fridge.FridgeRepository;
import org.springframework.fridgetracker.fridgecontents.Fridgecontents;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrocerylistController {
	@Autowired
	private GrocerylistRepository grocerylistRepository;
	@Autowired
	private FridgeRepository fridgeRepository;

	private final Logger logger = LoggerFactory.getLogger(GrocerylistController.class);

	@RequestMapping(method = RequestMethod.GET, path = "/grocerylist")
	public List<Grocerylist> getAllGrocerylists() {
		List<Grocerylist> results = grocerylistRepository.findAll();
		logger.info("Number of Grocery Lists Fetched:" + results.size());
		return results;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/grocerylist/{id}")
	public Optional<Grocerylist> getGroceryListByFridge(@PathVariable("id") Integer id) {
		Optional<Grocerylist> list = grocerylistRepository.findById(id);
		return list;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/grocerylist/new")
	public Map<String, String> saveGrocerylistItem(@RequestBody Grocerylist grocerylist) {
		if (grocerylist == null)
			throw new RuntimeException("Item was not provided in creation statement");
		HashMap<String, String> map = new HashMap<>();
		if (grocerylist.getFridge() == null) {
			map.put("grocery item added", "false");
			map.put("reason", "Fridge does not exist");
			return map;
		}
		Fridge f = fridgeRepository.getOne(grocerylist.getFridge().getId());
		grocerylist.setFridge(f);
		grocerylist = grocerylistRepository.save(grocerylist);
		f.addGrocerylist(grocerylist);
		fridgeRepository.save(f);
		map.put("grocery item added", "true");
		return map;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/grocerylist/delete/{id}")
	public Map<String, String> deleteGrocerylistItem(@PathVariable("id") Integer id) {
		if (id == null)
			throw new RuntimeException("ID was not provided in delete statement");
		HashMap<String, String> map = new HashMap<>();
		if (!grocerylistRepository.findById(id).isPresent()) {
			map.put("grocery item deleted", "false");
			map.put("reason", "Item does not exist");
		} else {
			grocerylistRepository.deleteById(id);
			map.put("grocery item deleted", "true");
		}
		return map;
	}
}
