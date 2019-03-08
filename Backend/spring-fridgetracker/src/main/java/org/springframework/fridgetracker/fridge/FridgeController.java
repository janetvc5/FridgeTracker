package org.springframework.fridgetracker.fridge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Fridge> getAllFridgeUsers() {
        List<Fridge> results = fridgeRepository.findAll();
        logger.info("Number of owners Fetched:" + results.size());
        return results;
    }
	
}
