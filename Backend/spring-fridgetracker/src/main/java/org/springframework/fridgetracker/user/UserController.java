package org.springframework.fridgetracker.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.hibernate.mapping.Set;
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
		HashMap<String,String> map = new HashMap<>();
		if(userRepository.findByUsername(user.getUsername()).isPresent()) {
			map.put("user creation success","false");
			map.put("reason","Username already exists");
			return map;
		}
		if(user.getFridge()==null) {
			// Creates a new fridge, saves to repository, and fetches the id
			Fridge f = fridgeRepository.save(new Fridge());
			user.setFridge(f);
		} else if(!fridgeRepository.findById(user.getFridge().getId()).isPresent()) {
			map.put("user creation success","false");
			map.put("reason","Fridge does not exist");
			return map;
		}
		user = userRepository.save(user);
		Fridge f = fridgeRepository.getOne(user.getFridge().getId());
		List<User> s = new ArrayList();
		s.add(user);
		f.setUsers(s);
		f.setFridgecontents(new ArrayList());
		fridgeRepository.save(f);
		map.put("user creation success","true");
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
	
	/*
	 * Takes a username and password, and gets an optional. If it is not present, or if the password
	 * is incorrect, a login attempt failure message is returned. Else, it returns a success, and a user
	 * id.
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/login")
	public Map<String,String> login(@RequestBody User loginAttempt) {
		HashMap<String,String> map = new HashMap<>();
        Optional<User> u = userRepository.findByUsername(loginAttempt.getUsername());
        if(!u.isPresent()) {
        	map.put("login success", "false");
        } else if(loginAttempt.getPassword()!=u.get().getPassword()) {
        	map.put("login success", "false");
        } else {
        	map.put("login success","true");
        	map.put("id",u.get().getId().toString());
        }
        return map;
    }
}
