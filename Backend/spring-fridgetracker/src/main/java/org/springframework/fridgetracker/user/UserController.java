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

	/*
	 * This creates a new user, and a new fridge if the user doesn't have one.
	 * 
	 * It will throw a RuntimeException if a user is not provided in the UserCreationStatement.
	 * It will return JSON object with a login success statement, with both a userID and a fridgeID.
	 * 
	 * A request is built like such (fridge portion is optional, the order of these objects is not important):
	 * {
	 * 		"user": {
	 * 			"firstName", (String)
	 * 			"lastName", (String)
	 * 			"role", (Integer)
	 * 			"userName", (String)
	 * 			"password" (String)
	 * 		},
	 * 		"fridge": {
	 * 			"fridgeId" (Integer)
	 * 		}
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/new")
	public Map<String,String> saveUser(@RequestBody UserCreationStatement UCS) {
		User user = UCS.getUser();
		if(user==null) throw new RuntimeException("User was not provided in creation statement");
		Fridge fridge = UCS.getFridge();
		user = userRepository.save(user);
		//	Creates new fridge if there is none provided (Generation.auto would not work)
		if(fridge==null) {
			fridge = new Fridge();
			Random r = new Random();
			Integer i = r.nextInt(999999);;
			Optional<Fridge> e = fridgeRepository.findByFridgeid(i);
			while(e.isPresent()) {
				i = r.nextInt(999999);;
				e = fridgeRepository.findByFridgeid(i);
			}
			fridge.setFridgeid(i);
		}
		fridge.setUserid(user.getId());
		fridge = fridgeRepository.save(fridge);
		System.out.println(fridge.toString());
		HashMap<String,String> map = new HashMap<>();
		map.put("login success","true");
		map.put("userID",user.getId().toString());
		map.put("fridgeID",fridge.getFridgeid().toString());
		return map;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user")
	public List<User> getAllUsers() {
        List<User> results = userRepository.findAll();
        logger.info("Number of users Fetched:" + results.size());
        return results;
    }

	@RequestMapping(method = RequestMethod.GET, path = "/user/u/{user}")
	public Optional<User> findUserByUserName(@PathVariable("username") String username) {
		logger.info("Entered into Controller Layer");
		Optional<User> results = userRepository.findByUsername(username);
		return results;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/user/id/{userId}/fridge")
	public Optional<Fridge> findFridgesByUserId(@PathVariable("username") Integer userId) {
		logger.info("Entered into Controller Layer");
		Optional<Fridge> results = fridgeRepository.findByUserid(userId);
		return results;
	}
	

}
