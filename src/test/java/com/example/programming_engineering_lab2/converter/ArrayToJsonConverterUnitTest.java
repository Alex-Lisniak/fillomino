package com.example.programming_engineering_lab2.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArrayToJsonConverterUnitTest {
    private final String testKey = "testKey";
    private ArrayToJsonConverter converter;
    @Before
    public void setup() {
        converter = spy(ArrayToJsonConverter.class);
    }
    @Test
    public void convertingTest() throws JSONException {
        int array[][] = {
                {10, 20},
                {30, 40}
        };
        String result = "result";
        JSONObject jsonObject = mock(JSONObject.class);

        when(jsonObject.get(testKey)).thenReturn(result);
        when(converter.convertArrayToJson(array)).thenReturn(jsonObject);

        String string = (String)converter.convertArrayToJson(array).get(testKey);
        assertEquals(string, result);
    }
}
