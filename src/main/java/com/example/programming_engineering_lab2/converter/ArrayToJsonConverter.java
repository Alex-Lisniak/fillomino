package com.example.programming_engineering_lab2.converter;


import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ArrayToJsonConverter {
    public JSONObject convertArrayToJson(int array[][]) {
        Map<String, List<Integer>> values = new HashMap<>();
        List<Integer> arrayValues = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                arrayValues.add(array[i][j]);
            }
            values.put(Integer.toString(i), arrayValues);
            arrayValues = new ArrayList<>();
        }
        JSONObject jsonObject = new JSONObject(values);
        return jsonObject;
    }
}

