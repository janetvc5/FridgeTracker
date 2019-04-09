package com.example.fridge_tracker;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoTest {

    // @Mock
    //    MyDatabase databaseMock;    //Tells Mockito to mock the databaseMock instance

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); // Tells Mockito to create the mocks based on the @Mock annotation

    @Test
    public void testSearch() {
        // Creates Mock object of the class that isn't fully implemented
        SearchReturn t = mock(SearchReturn.class);
        SearchActivity testItemReturnSuccess = new SearchActivity();

        String searchInput = "apple"
        JSONObject response = new JSONObject();
        try{
            response.put("Apple",79);
            response.put("Freeze Dried Apple",110);
            response.put("Apple Pie",220);
        }
        catch(JSONException e){

        }

        when(t.getResponse("apple")).thenReturn(response);
        Assert.assertEquals(testItemReturnSuccess.)
    }
}
