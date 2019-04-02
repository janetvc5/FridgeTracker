package com.example.fridge_tracker;

import android.support.v4.widget.TextViewCompat;

import static org.mockito.Mockito.*;

public class MockitoTest {

    @Mock
    MyDatabase databaseMock;    //Tells Mockito to mock the databaseMock instance

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); // Tells Mockito to create the mocks based on the @Mock annotation

    @Test
    public void testQuery(){
        ClassToTest t = new ClassToTest(databaseMock);  // Instantiates the class under test using the created mock
        boolean check = t.query("* from t");            // Executes some code of the class under test
        assertTrue(check);                              // Asserts that the method call returned true
        verify(databaseMock).query("* from t");         // Verify that the query method was called on the MyDatabase mock
    }
}
