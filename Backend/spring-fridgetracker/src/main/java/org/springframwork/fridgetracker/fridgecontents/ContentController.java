package org.springframwork.fridgetracker.fridgecontents;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {
	@Autowired
	private ContentRepository contentRepository;
	
	private final Logger logger = LoggerFactory.getLogger(ContentController.class);
	

	@RequestMapping(method = RequestMethod.POST, path = "/content/add")
	public Map<String, String> saveContent(@RequestBody Content content) {
		if (content == null) {
			throw new RuntimeException("Content Empty");
		}
		if (content.getFridgeid() == null) {
			throw new RuntimeException("Content Not Assosiated With FridgeId");
		}
		content = contentRepository.save(content);
		HashMap<String, String> map = new HashMap<>();
		map.put("Content Added", "true");
		return map;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/content/remove")
	public Map<String, String> deleteContent(@PathVariable("idfood") Integer idfood) {
		logger.info("Fetching & Deleting Content With Id {}", idfood);
		Optional<Content> content = contentRepository.findById(idfood);
		if (content == null) {
			throw new RuntimeException("Content Empty");
		}
		contentRepository.deleteById(idfood);
		HashMap<String, String> map = new HashMap<>();
		map.put("Content Removed", "true");
		return map;
	}
	
	
	
	
}
