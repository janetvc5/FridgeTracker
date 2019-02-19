package org.springframework.fridgetracker.owner;

import java.util.List;
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
	public String saveUser(User user) {
		userRepository.save(user);
		return "New User " + user.getId() + " Saved";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user")
	public List<User> getAllUsers() {
        List<User> results = userRepository.findAll();
        logger.info("Number of owners Fetched:" + results.size());
        return results;
    }

	@RequestMapping(method = RequestMethod.GET, path = "/owner/{ownerId}")
	public Optional<User> findUserById(@PathVariable("id") int id) {
		logger.info("Entered into Controller Layer");
		Optional<User> results = userRepository.findById(id);
		return results;
	}

}
