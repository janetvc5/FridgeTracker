package org.springframework.fridgetracker.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.fridgetracker.fridge.FridgeRepository;
import org.springframework.fridgetracker.fridge.Fridge;

@RestController
class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FridgeRepository fridgeRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.POST, path = "/user/new")
	public Map<String,String> saveUser(@RequestBody User user) {
		if(user==null) throw new RuntimeException("User was not provided in creation statement");
		if(user.getFridgeid()==null) {
			// Creates a new fridge, saves to repository, and fetches the id
			user.setFridgeid(fridgeRepository.save(new Fridge()).getId());
		}
		user = userRepository.save(user);
		HashMap<String,String> map = new HashMap<>();
		map.put("login success","true");
		map.put("userId",user.getId().toString());
		return map;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user")
	public List<User> getAllUsers() {
        List<User> results = userRepository.findAll();
        logger.info("Number of users Fetched:" + results.size());
        return results;
    }
	@RequestMapping(method = RequestMethod.GET, path = "/user/{userId}")
	public Optional<User> findUserByUserName(@PathVariable("userId") Integer userId) {
		logger.info("Entered into Controller Layer");
		Optional<User> results = userRepository.findById(userId);
		return results;
	}
	

}
