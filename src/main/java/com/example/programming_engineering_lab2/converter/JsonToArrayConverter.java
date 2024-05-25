package com.example.programming_engineering_lab2.converter;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JsonToArrayConverter {
    public void convertJsonToArray(JSONObject jsonObject, int arrayToFill[][]) {
        int columnSize = arrayToFill.length;
        int rowSize = arrayToFill[0].length;
        for (int i = 0; i < columnSize; i++) {
            JSONArray jsonArray = jsonObject.getJSONArray(Integer.toString(i));
            for (int j = 0; j < rowSize; j++) {
                arrayToFill[i][j] = ((Integer)jsonArray.get(j)).intValue();
            }

        }
    }
}
