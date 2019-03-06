package org.springframework.fridgetracker.owner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {
	@Autowired
	private UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.POST, path = "/user/new")
	public Map<String,String> saveUser(@RequestBody User user) {
		userRepository.save(user);
		HashMap<String,String> map = new HashMap<>();
		map.put("login success","true");
		//return "New User " + user.getId() + " Saved";
		return map;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user")
	public List<User> getAllUsers() {
        List<User> results = userRepository.findAll();
        logger.info("Number of owners Fetched:" + results.size());
        return results;
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
	public Optional<User> findUserById(@PathVariable("id") Integer id) {
		logger.info("Entered into Controller Layer");
		Optional<User> results = userRepository.findById(id);
		return results;
	}
	

}
