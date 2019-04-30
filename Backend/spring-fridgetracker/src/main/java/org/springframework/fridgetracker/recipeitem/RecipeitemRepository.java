package org.springframework.fridgetracker.recipeitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeitemRepository extends JpaRepository<Recipeitem, Integer>{

}
