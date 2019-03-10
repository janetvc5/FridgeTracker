package org.springframework.fridgetracker.fridge;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Integer>{

}
