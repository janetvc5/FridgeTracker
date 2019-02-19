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
class OwnerController {
	@Autowired
	private OwnerRepository ownerRepository;

	private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

	@RequestMapping(method = RequestMethod.POST, path = "/owner/new")
	public String saveOwner(@RequestBody Owner owner) {
		ownerRepository.save(owner);
		return "New User " + owner.getId() + " Saved";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/owner")
	public List<Owner> getAllOwners() {
        List<Owner> results = ownerRepository.findAll();
        logger.info("Number of owners Fetched:" + results.size());
        return results;
    }

	@RequestMapping(method = RequestMethod.GET, path = "/owner/{ownerId}")
	public Optional<Owner> findOwnerById(@PathVariable("ownerId") int id) {
		logger.info("Entered into Controller Layer");
		Optional<Owner> results = ownerRepository.findById(id);
		return results;
	}

}
