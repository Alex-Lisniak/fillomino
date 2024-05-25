package com.example.programming_engineering_lab2.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.Random;

@UtilityClass
public final class PuzzleGeneratorUtil {
    public static final int defaultSize = 10;
    private static int alreadyCreatedCounter = 0;

    public static void generatePuzzle(int [][] array) {
        Random random = new Random();
        int initialRandom = random.nextInt(defaultSize - 2) + 2;
        int initialCounter = initialRandom;
        array[0][0] = initialCounter;
        for (int i = 0; i < defaultSize; i ++) {
            for (int j = 0; j < defaultSize; j++) {
                if (initialCounter > 0) {
                    if (initialCounter == initialRandom) {
                        array[i][j] = initialRandom;
                        initialCounter--;
                        continue;
                    }
                    else {
                        array[i][j] = 0;
                        initialCounter--;
                        continue;
                    }
                }
                initialRandom = random.nextInt(defaultSize - 2) + 2;
                initialCounter = initialRandom;;
            }
        }
        alreadyCreatedCounter += 1;
    }
}
