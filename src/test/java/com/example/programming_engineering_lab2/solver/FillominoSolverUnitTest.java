package com.example.programming_engineering_lab2.solver;


import com.example.programming_engineering_lab2.exception.NoSolutionFoundException;
import com.example.programming_engineering_lab2.util.FillominoSolver;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FillominoSolverUnitTest {
    FillominoSolver fillominoSolver = mock(FillominoSolver.class);
    @Test
    public void anyExceptionTest() {
        doNothing().when(fillominoSolver).solve();
    }
    @Test
    public void successPuzzleTest() {
         int puzzle[][] =  {
                {0, 3, 5, 0, 5, 4, 0, 4, 4, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 6},
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 2, 6, 2, 6, 0, 0, 0},
                {6, 0, 0, 6, 0, 0, 2, 0, 0, 1},
                {1, 0, 0, 2, 0, 0, 6, 0, 0, 2},
                {0, 0, 0, 6, 2, 6, 2, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 6},
                {0, 3, 4, 0, 4, 6, 0, 2, 1, 0},
        };
        int initialPuzzle[][] = new int[puzzle.length][puzzle[0].length];
        copyArray(puzzle, initialPuzzle);
        FillominoSolver solver = new FillominoSolver(puzzle);
        solver.solve();
        assertFalse(!isArraysEqual(puzzle, initialPuzzle));
    }
    @Test(expected = NoSolutionFoundException.class)
    public void exceptionTest() {
        int puzzle[][] =  {
                {0, 0},
                {0,0}
        };
        FillominoSolver solver = new FillominoSolver(puzzle);
        solver.solve();
    }
    private boolean isArraysEqual(int arrayForCheck[][], int initialArray[][]) {
        for (int i = 0; i < arrayForCheck.length; i++){
            for (int j = 0; j < arrayForCheck[0].length; j++) {
                if (arrayForCheck[i][j] == initialArray[i][j]) return true;
            }
        }
        return false;
    }
    private void copyArray(int initialArray[][], int arrayForCopy[][]){
        for (int i = 0; i < initialArray.length; i++) {
            for (int j = 0; j < initialArray[0].length; j++) {
                arrayForCopy[i][j] = initialArray[i][j];
            }
        }
    }
}
