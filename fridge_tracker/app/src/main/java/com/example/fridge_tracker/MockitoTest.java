package com.example.fridge_tracker;

import android.support.v4.widget.TextViewCompat;

import static org.mockito.Mockito.*;

public class MockitoTest {

    @Mock
    MyDatabase databaseMock;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testQuery(){
        ClassToTest t = new ClassToTest(databaseMock);
        boolean check = t.query("* from t");
        assertTrue(check);
        verify(databaseMock).query("* from t");

    }
}
