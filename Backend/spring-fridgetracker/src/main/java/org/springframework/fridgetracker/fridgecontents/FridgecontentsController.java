package org.springframework.fridgetracker.fridgecontents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.fridge.Fridge;
import org.springframework.fridgetracker.fridge.FridgeRepository;
import org.springframework.fridgetracker.system.NotFoundException;
import org.springframework.fridgetracker.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FridgecontentsController {
	@Autowired
	private FridgecontentsRepository fridgecontentsRepository;
	@Autowired
	private FridgeRepository fridgeRepository;

	private final Logger logger = LoggerFactory.getLogger(FridgecontentsController.class);

	/**
	 * Gets all fridge contents
	 * @return List of Fridge contents
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/fridgecontents")
	public List<Fridgecontents> getAllContents() {
        List<Fridgecontents> results = fridgecontentsRepository.findAll();
        logger.info("Number Contents Fetched:" + results.size());
        return results;
    }

	/**
	 * Gets an item of a fridge item
	 * @param id id of item to get (in url)
	 * @return Optional of Fridge contents
	 */
	@RequestMapping(method = RequestMethod.GET, path="/fridgecontents/{id}")
	public Optional<Fridgecontents> getContentsById(@PathVariable("id") Integer id) {
		Optional<Fridgecontents> f = fridgecontentsRepository.findById(id);
		return f;
	}
	
	/**
	 * Adds an item to a fridge
	 * @param fridgecontents Item to add (in request body)
	 * @return Map
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/fridgecontents/new")
	public Map saveFridgecontents(@RequestBody Fridgecontents fridgecontents) {
		if(fridgecontents==null) throw new RuntimeException("Item was not provided in creation statement");
		HashMap map = new HashMap<>();
		if(fridgecontents.getFridge()==null) {
			map.put("fridge item added", "false");
			map.put("reason", "Fridge does not exist");
			return map;
		}
		Fridge f = fridgeRepository.getOne(fridgecontents.getFridge().getId());
		fridgecontents.setFridge(f);
		fridgecontents = fridgecontentsRepository.save(fridgecontents);
		f.addFridgecontents(fridgecontents);
		fridgeRepository.save(f);
		map.put("fridge item added","true");
		return map;
	}
	
	/**
	 * Deletes a fridgecontents item by id
	 * @param id Id of item to delete (in URL)
	 * @return Map
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/fridgecontents/delete/{id}")
	public Map deleteFridgecontents(@PathVariable("id") Integer id) {
		if(id==null)  throw new RuntimeException("ID was not provided in delete statement");
		HashMap map = new HashMap<>();
		if(!fridgecontentsRepository.findById(id).isPresent()) {
			map.put("fridge item deleted", "false");
			map.put("reason", "Item does not exist");
		} else {
			fridgecontentsRepository.deleteById(id);
			map.put("fridge item deleted","true");
		}
		return map;
	}

}
