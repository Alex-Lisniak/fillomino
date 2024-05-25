package com.example.programming_engineering_lab2.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class JsonToArrayConverterUnitTest {
    private final String testKey = "testKey";
    private JsonToArrayConverter converter;
    @Before
    public void setup() {
        converter = spy(JsonToArrayConverter.class);
    }
    @Test
    public void convertingTest() throws JSONException {
        int array[][] = {
                {10, 20},
                {30, 40}
        };
        int changedArray[][] = new int[array.length][array[0].length];
        String result = "result";
        JSONObject jsonObject = mock(JSONObject.class);
        //int arrayFirstHalf[] = array[0];
        JSONArray jsonArrayFirstHalf = new JSONArray(array[0]);
        JSONArray jsonArraySecondHalf = new JSONArray(array[1]);

        when(jsonObject.get(testKey)).thenReturn(result);
        when(jsonObject.getJSONArray("0")).thenReturn(jsonArrayFirstHalf);
        when(jsonObject.getJSONArray("1")).thenReturn(jsonArraySecondHalf);

        converter.convertJsonToArray(jsonObject, changedArray);
        assertTrue(checkArraysEquality(array, changedArray));
    }
    private boolean checkArraysEquality(int initialArray[][], int arrayForChecking[][]){
        if (initialArray.length != arrayForChecking.length) return false;
        if (initialArray[0].length != arrayForChecking[0].length) return false;
        for (int i= 0; i < initialArray.length; i++) {
            for (int j = 0; j < initialArray[0].length; j++) {
                if (initialArray[i][j] != arrayForChecking[i][j]) return false;
            }
        }
        return true;
    }
}
