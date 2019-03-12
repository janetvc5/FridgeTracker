package org.springframework.fridgetracker.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
	@Autowired
	private ItemRepository itemRepository;

	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@RequestMapping(method = RequestMethod.POST, path = "/item/new")
	public Map<String,String> saveItem(@RequestBody Item item) {
		
		itemRepository.save(item);
		HashMap<String,String> map = new HashMap<>();
		map.put("Item Saved","true");
		return map;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/item")
	public List<Item> getItemList() {
        List<Item> results = itemRepository.findAll();
        logger.info("Number of items Fetched:" + results.size());
        return results;
    }
}
