package com.example.fridge_tracker;

import java.util.ArrayList;

public interface MockReqs {

    //create object
     ArrayList<String> getAllFridges();

     String displayGroceryShopper();

     boolean checkLoggedIn(String user);

    // ArrayList<String> checkExpiredFood();
}
