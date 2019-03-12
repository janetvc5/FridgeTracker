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

	@RequestMapping(method = RequestMethod.GET, path = "/fridgecontents")
	public List<Fridgecontents> getAllFridgecontents() {
        List<Fridgecontents> results = fridgecontentsRepository.findAll();
        logger.info("Number of fridges Fetched:" + results.size());
        return results;
    }

	@RequestMapping(method = RequestMethod.GET, path="/fridgecontents/{id}")
	public Optional<Fridgecontents> getAllFridgeUsers(@PathVariable("id") Integer id) {
		Optional<Fridgecontents> f = fridgecontentsRepository.findById(id);
		return f;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/fridgecontents/new")
	public Map<String,String> saveFridgecontents(@RequestBody Fridgecontents fridgecontents) {
		if(fridgecontents==null) throw new RuntimeException("Item was not provided in creation statement");
		HashMap<String,String> map = new HashMap<>();
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
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/fridgecontents/delete/{id}")
	public Map<String,String> deleteFridgecontents(@PathVariable("id") Integer id) {
		if(id==null)  throw new RuntimeException("ID was not provided in delete statement");
		HashMap<String,String> map = new HashMap<>();
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
