package org.springframework.fridgetracker.fridge;

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

	@RequestMapping(method = RequestMethod.POST, path = "/fridge/new")
	public Map<String,String> saveFridge(@RequestBody Fridge fridge) {
		fridgeRepository.save(fridge);
		HashMap<String,String> map = new HashMap<>();
		map.put("login success","true");
		return map;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/fridge")
	public List<Fridge> getAllFridges() {
        List<Fridge> results = fridgeRepository.findAll();
        logger.info("Number of fridges Fetched:" + results.size());
        return results;
    }
	
	@RequestMapping(method = RequestMethod.GET, path="/fridge/{id}/users")
	public List getAllFridgeUsers(@PathVariable("id") Integer id) {
		Optional<Fridge> f = fridgeRepository.findById(id);
		if(!f.isPresent()) throw new NotFoundException(id);
		return f.get().getUsers();
	}
	
}
