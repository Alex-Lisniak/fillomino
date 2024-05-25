package com.example.programming_engineering_lab2.controller;


import com.example.programming_engineering_lab2.converter.ArrayToJsonConverter;
import com.example.programming_engineering_lab2.converter.JsonToArrayConverter;

import com.example.programming_engineering_lab2.service.PuzzleService;
import com.example.programming_engineering_lab2.service.impl.AuthKeyServiceImpl;
import com.example.programming_engineering_lab2.service.impl.PuzzleServiceImpl;
import com.example.programming_engineering_lab2.validation.AuthKeyValidator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PuzzleControllerTest {

    private AuthKeyValidator authKeyValidator = mock(AuthKeyValidator.class);
    private AuthKeyServiceImpl authKeyService = mock(AuthKeyServiceImpl.class);
    private PuzzleServiceImpl puzzleService = mock(PuzzleServiceImpl.class);
    private String result = "result";
    private final UUID testUUID = UUID.fromString("4cbb2a7b-be0a-456e-b5b8-c951d05c4119");
    private String stringPuzzle = "{\"0\":[0,3,5,0,5,4,0,4,4,0],\"1\":[1,0,0,0,0,0,0,0,0,6],\"2\":[3,0,0,0,0,0,0,0,0,3],\"3\":[0,0,0,2,6,2,6,0,0,0],\"4\":[6,0,0,6,0,0,2,0,0,1],\"5\":[1,0,0,2,0,0,6,0,0,2],\"6\":[0,0,0,6,2,6,2,0,0,0],\"7\":[3,0,0,0,0,0,0,0,0,5],\"8\":[2,0,0,0,0,0,0,0,0,6],\"9\":[0,3,4,0,4,6,0,2,1,0]}";
    @Before
    public void setup () {
        when(authKeyValidator.validate(testUUID)).thenReturn(true);
    }

    @Test
    public void getExamplePuzzleTest() {
        ArrayToJsonConverter arrayToJsonConverter = spy(ArrayToJsonConverter.class);
        JsonToArrayConverter jsonConverter = mock(JsonToArrayConverter.class);
        PuzzleController puzzleController = new PuzzleController(arrayToJsonConverter, jsonConverter, puzzleService, authKeyService, authKeyValidator);
        ResponseEntity responseEntity = puzzleController.getExamplePuzzle(testUUID);

        assertEquals(responseEntity.getBody().toString(), stringPuzzle);
    }
    @Test
    public void getExamplePuzzleWithMockTest() {
        ArrayToJsonConverter arrayToJsonConverter = mock(ArrayToJsonConverter.class);
        JsonToArrayConverter jsonConverter = mock(JsonToArrayConverter.class);
        PuzzleController puzzleController = new PuzzleController(arrayToJsonConverter, jsonConverter, puzzleService, authKeyService, authKeyValidator);
        Map<String, Integer> randomMap= new HashMap<>();
        randomMap.put("first", 15);
        randomMap.put("second", 22);
        JSONObject jsonObject = new JSONObject(randomMap);

        when(arrayToJsonConverter.convertArrayToJson(any())).thenReturn(jsonObject);

        ResponseEntity responseEntity = puzzleController.getExamplePuzzle(testUUID);

        assertEquals(responseEntity.getBody().toString(), "{\"first\":15,\"second\":22}");
    }
    @Test(expected = StackOverflowError.class)
    public void solvePuzzleExceptionTest() throws JSONException {
        ArrayToJsonConverter arrayToJsonConverter = mock(ArrayToJsonConverter.class);
        JsonToArrayConverter jsonConverter = mock(JsonToArrayConverter.class);
        PuzzleController puzzleController = new PuzzleController(arrayToJsonConverter, jsonConverter, puzzleService, authKeyService, authKeyValidator);

        JSONObject conditions = new JSONObject(stringPuzzle);
        JSONObject mockedJson = mock(JSONObject.class);

        when(arrayToJsonConverter.convertArrayToJson(any())).thenReturn(mockedJson);
        when(mockedJson.toString()).thenReturn(result);
        doNothing().when(jsonConverter).convertJsonToArray(any(), any());


        ResponseEntity  actualResult = puzzleController.solvePuzzle(testUUID, conditions);
        assertEquals(result, actualResult.toString());
    }
    @Test
    public void getPuzzleCondition() {
        ArrayToJsonConverter arrayToJsonConverter = mock(ArrayToJsonConverter.class);
        JsonToArrayConverter jsonConverter = mock(JsonToArrayConverter.class);
        PuzzleController puzzleController = new PuzzleController(arrayToJsonConverter, jsonConverter, puzzleService, authKeyService, authKeyValidator);
        JSONObject jsonObject = mock(JSONObject.class);

        when(arrayToJsonConverter.convertArrayToJson(any())).thenReturn(jsonObject);
        when(jsonObject.toString()).thenReturn(result);

        ResponseEntity actualResult =  puzzleController.getPuzzleCondition(testUUID);
        assertEquals(actualResult.getBody().toString(), result);
    }
}
