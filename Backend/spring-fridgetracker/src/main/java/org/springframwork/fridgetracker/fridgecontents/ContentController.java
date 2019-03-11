package org.springframwork.fridgetracker.fridgecontents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {
	@Autowired
	private ContentRepository contentRepository;
	
	private final Logger logger = LoggerFactory.getLogger(ContentController.class);
	
	
	
}
