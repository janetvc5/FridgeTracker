package org.springframework.fridgetracker.grocerylist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface GrocerylistRepository extends JpaRepository<Grocerylist, Integer>  {

}
