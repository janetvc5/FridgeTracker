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

	/**
	 * Adds a user on a POST call to "{url}/user/new"
	 * @param user - User to add to the record (in request body)
	 * @return - Returns a Map with a creation status (true or false) and reason
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/new")
	public Map saveUser(@RequestBody User user) {
		if(user==null) throw new RuntimeException("User was not provided in creation statement");
		HashMap map = new HashMap<>();
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

	/**
	 * Returns all users on a GET call to "{url}/user"
	 * @return - List of Users
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/user")
	public List<User> getAllUsers() {
        List<User> results = userRepository.findAll();
        logger.info("Number of users Fetched:" + results.size());
        return results;
    }
	
	/**
	 * Gets a user by the given ID on a GET call to "{url}/user/{userId}"
	 * @param userId - ID of user to get (part of url)
	 * @return - Optional (if user is in, it will have a value)
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/user/{userId}")
	public Optional<User> findUserById(@PathVariable("userId") Integer userId) {
		logger.info("Entered into Controller Layer");
		Optional<User> results = userRepository.findById(userId);
		return results;
	}
	
	/*
	 * Takes a username and password, and gets an optional. If it is not present, or if the password
	 * is incorrect, a login attempt failure message is returned. Else, it returns a success, and a user
	 * id.
	 */
	/**
	 * Takes a username and password, and verifies if they are correct
	 * @param loginAttempt - User object with just Username and Password values (part of request body)
	 * @return - a map with a login success key, false if login was unsuccessful, true if successful, and if successful, key "id" will have the user id
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/login")
	public Map login(@RequestBody User loginAttempt) {
		HashMap map = new HashMap<>();
        Optional<User> u = userRepository.findByUsername(loginAttempt.getUsername());
        if(!u.isPresent()) {
        	map.put("login success", "false");
        } else if(loginAttempt.getPassword().compareTo(u.get().getPassword())!=0) {
        	map.put("login success", "false");
        } else {
        	map.put("login success","true");
        	map.put("id",u.get().getId().toString());
        }
        return map;
    }
}
