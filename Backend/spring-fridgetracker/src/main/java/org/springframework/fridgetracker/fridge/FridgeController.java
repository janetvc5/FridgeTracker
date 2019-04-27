package org.springframework.fridgetracker.fridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.system.NotFoundException;
import org.springframework.fridgetracker.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FridgeController {
	@Autowired
	private FridgeRepository fridgeRepository;

	private final Logger logger = LoggerFactory.getLogger(FridgeController.class);

	/**
	 * Makes a new fridge
	 * @param fridge fridge to save (in request body)
	 * @return Map
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/fridge/new")
	public Map saveFridge(@RequestBody Fridge fridge) {
		fridgeRepository.save(fridge);
		HashMap map = new HashMap<>();
		map.put("login success","true");
		return map;
	}
	
	/**
	 * Gets all fridges
	 * @return List of fridges
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/fridge")
	public List<Fridge> getAllFridges() {
        List<Fridge> results = fridgeRepository.findAll();
        logger.info("Number of fridges Fetched:" + results.size());
        return results;
    }
	
	/**
	 * Gets all users in a specific fridge
	 * @param id ID of fridge (in URL)
	 * @return List of users
	 */
	@RequestMapping(method = RequestMethod.GET, path="/fridge/{id}/users")
	public List getAllFridgeUsers(@PathVariable("id") Integer id) {
		Optional<Fridge> f = fridgeRepository.findById(id);
		if(!f.isPresent()) throw new NotFoundException(id);
		return f.get().getUsers();
	}
	
	/**
	 * Gets all fridge contents of a specified fridge
	 * @param id ID of fridge (in url)
	 * @return List of fridgecontents
	 */
	@RequestMapping(method = RequestMethod.GET, path="/fridge/{id}/contents")
	public List getAllFridgecontents(@PathVariable("id") Integer id) {
		Optional<Fridge> f = fridgeRepository.findById(id);
		if(!f.isPresent()) throw new NotFoundException(id);
		return f.get().getFridgecontents();
	}
	
}
