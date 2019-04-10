package com.example.fridge_tracker;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.manipulation.Sorter;

import java.util.ArrayList;


import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.List;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class mockitoTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void GetFridgesTest_returnsTrue() {

        //The mocked (unimplemented) class that has a non-functional method (.getEventList())
        MockReqs m = mock(MockReqs.class);

        when(m.getAllFridges()).thenReturn(response);

        List<String> simulatedResponse = new ArrayList<>();
        List<String> expectedResponse = new ArrayList<>();
        List<String> fetchedResponse = new ArrayList<>();
        List<String> sortedResponse = new ArrayList<>();

        simulatedResponse
        //Mocks behavior of calling unimplemented method (.getEventList())
        when(m.getAllFridges()).thenReturn(fetchedResponse);

        //Adds the event list into a list that will be sorted
        simulatedResponse.addAll(m.getAllFridges());

        for (int i = 0; i < sortedResponse.size(); i++) {
            assertSame(expectedResponse.get(i), sortedResponse.get(i));
        }

    }

    @Test
    public void displayGroceryShopper_returnTrue() throws JSONException {
        MockReqs m = mock(MockReqs.class);

        JSONObject response = new JSONObject();

        String fridgeID = "fridge123";

        response.put("shopper", "Hailey");

        when(m.displayGroceryShopper(fridgeID).thenReturn(response);

        Assert.assertEquals(response, "Hailey");

    }

    @Test
    public void checkLoggedIn_returnTrue() throws JSONException {
        MockReqs m = mock(MockReqs.class);

        JSONObject response = new JSONObject();

        String loggedInUser = "Hailey";

        response.put("loggedIn", new Boolean(true));

        when(m.checkLoggedIn(loggedInUser)).thenReturn(response);

        Assert.assertEquals(response, true);

    }
}
