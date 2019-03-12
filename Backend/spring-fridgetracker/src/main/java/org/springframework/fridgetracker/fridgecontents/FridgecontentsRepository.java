package org.springframework.fridgetracker.fridgecontents;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FridgecontentsRepository extends JpaRepository<Fridgecontents, Integer> {

}
