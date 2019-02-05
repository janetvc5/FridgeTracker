package com.example.demo.fridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.fridge.Fridge;
import com.example.demo.fridge.FridgeRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
@RestController
public class FridgeController {

	@Autowired
	EntityManager em;
    @Autowired
    FridgeRepository fridgeRepository;
    
    private final Logger logger = LoggerFactory.getLogger(FridgeController.class);
	
	@RequestMapping(method = RequestMethod.GET, path = "/fridges")
    public List<Fridge> getAllOwners() {
        List<Fridge> results = fridgeRepository.findAll();
        logger.info("Number of fridges Fetched:" + results.size());
        return results;
    }
	@RequestMapping(method = RequestMethod.GET, path = "/fridges/{idE}")
    public List<Food> getFridgeContents(@PathVariable("idE") int id) {
        logger.info("Fetching foods in fridge "+id+"...");
        TypedQuery<Food> query = em.createQuery("SELECT f FROM Food AS f WHERE f.id="+id,Food.class);
        List<Food> result = query.getResultList();
        return result;
    }
}
