package com.example.programming_engineering_lab2.util;


import com.example.programming_engineering_lab2.dto.IntegerPair;
import com.example.programming_engineering_lab2.exception.NoSolutionFoundException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FillominoSolver {

    private int[][] puzzle;
    private int[][] initialPuzzleForTracking;
    private boolean[][] visited;
    private int[] dr = {-1, 1, 0, 0}; // move rows
    private int[] dc = {0, 0, -1, 1}; // move columns
    private int rowSize;
    private int colSize;
    private boolean solutionFound;
    private static int counter = 0;

    public FillominoSolver(int[][] puzzle) {
        this.puzzle = puzzle;
        this.rowSize = puzzle.length;
        this.colSize = puzzle[0].length;
        this.visited = new boolean[rowSize][colSize];
        this.solutionFound = false;
        initialPuzzleForTracking = new int[puzzle.length][puzzle[0].length];
        copyArray(puzzle, initialPuzzleForTracking);
    }

    public void solve() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<?>> futures = new ArrayList<>();

        try {
            // Split the work into two tasks
            futures.add(executor.submit(() -> iterateValues(0, rowSize / 2)));
            futures.add(executor.submit(() -> iterateValues(rowSize / 2, rowSize)));

            // Wait for all tasks to complete
            for (Future<?> future : futures) {
                future.get();
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);

            printPuzzle();
        } catch (Exception e) {
            throw new NoSolutionFoundException("no solution for puzzle");
        }
    }

    private void iterateValues(int startRow, int endRow) {
        boolean hasNext = true;
        Point currentPoint = new Point(startRow, 0);
        while (hasNext) {
            Point point = findNextForIteration(currentPoint, startRow, endRow);
            if (point.x == -1 && point.y == -1) {
                hasNext = false;
                break;
            }
            currentPoint = point;
            counter = puzzle[point.x][point.y] - 1;
            dfs(point.x, point.y, -1);
        }
        checkAndFitEmptySpaces(startRow, endRow);
        System.out.println("gameEnded");
    }

    private Point findNextForIteration(Point previousPoint, int startRow, int endRow) {
        for (int i = previousPoint.x; i < endRow; i++) {
            for (int j = 0; j < initialPuzzleForTracking[0].length; j++) {
                if (initialPuzzleForTracking[i][j] != 0 && initialPuzzleForTracking[i][j] != 1) {
                    if (i == previousPoint.x && j <= previousPoint.y) continue;
                    System.out.println("Next point is " + initialPuzzleForTracking[i][j] + "(" + i + "|" + j + ")");
                    return new Point(i, j);
                }
            }
        }
        return new Point(-1, -1);
    }

    private void dfs(int r, int c, int direction) {
        if (!checkCoordinates(r, c)) return;
        if (counter <= 0) return;
        int num = puzzle[r][c];

        for (int i = 0; i < 4; i++) {
            if (!checkDirection(direction, i)) continue;
            int nr = r + dr[i];
            int nc = c + dc[i];
            checkAround(nr, nc, num, i);
            if (nr >= 0 && nr < rowSize && nc >= 0 && nc < colSize && !visited[nr][nc] && (puzzle[nr][nc] == 0 || puzzle[nr][nc] == num) && counter > 0) {
                puzzle[nr][nc] = num;
                visited[r][c] = true;
                System.out.println("insert " + num + " in " + nr + "/" + nc);
                printPuzzle();
                counter--;
                System.out.println("before internal dfs counter is " + counter);
                dfs(nr, nc, i);
            }
        }
    }

    private void checkAround(int i, int j, int num, int offsetIndex) {
        System.out.println("case is " + offsetIndex + " counter is " + counter);
        switch (offsetIndex) {
            case 0:
                checkWhenUp(i, j, num);
                break;
            case 1:
                checkWhenDown(i, j, num);
                break;
            case 2:
                checkWhenLeft(i, j, num);
                break;
            case 3:
                checkWhenRight(i, j, num);
                break;
        }
    }

    private boolean checkDirection(int direction, int offsetIndex) {
        if (Math.abs(direction - offsetIndex) == 1 &&
                !((direction == 1 && offsetIndex == 2) || (direction == 2 && offsetIndex == 1)))
            return false;
        return true;
    }

    private void checkWhenRight(int i, int j, int num) {
        checkUp(i, j, num);
        checkDown(i, j, num);
        checkRight(i, j, num);
    }

    public void checkWhenLeft(int i, int j, int num) {
        checkUp(i, j, num);
        checkDown(i, j, num);
        checkLeft(i, j, num);
    }

    public void checkWhenUp(int i, int j, int num) {
        checkUp(i, j, num);
        checkLeft(i, j, num);
        checkRight(i, j, num);
    }

    public void checkWhenDown(int i, int j, int num) {
        checkLeft(i, j, num);
        checkDown(i, j, num);
        checkRight(i, j, num);
    }

    private void checkLeft(int i, int j, int num) {
        if (i >= 0 && i < rowSize && (j - 1 >= 0) && (j - 1 < colSize)) { // check left
            if (initialPuzzleForTracking[i][j - 1] == num) {
                System.out.println("found value " + num + " in " + i + " / " + (j - 1) + " being on " + i + " / " + j);
                counter--;
            }
        }
    }

    private void checkRight(int i, int j, int num) {
        if (i >= 0 && i < rowSize && (j + 1 >= 0) && (j + 1 < colSize)) { //check right
            if (initialPuzzleForTracking[i][j + 1] == num) {
                System.out.println("found value " + num + " in " + i + " / " + (j + 1) + " being on " + i + " / " + j);
                counter--;
            }
        }
    }

    private void checkUp(int i, int j, int num) {
        if ((i - 1 >= 0) && (i - 1 < rowSize) && j >= 0 && j < colSize) { //check up
            if (initialPuzzleForTracking[i - 1][j] == num) {
                System.out.println("found value " + num + " in " + (i - 1) + " / " + j + " being on " + i + " / " + j);
                counter--;
            }
        }
    }

    private void checkDown(int i, int j, int num) {
        if ((i + 1 >= 0) && (i + 1 < rowSize) && j >= 0 && j < colSize) { //check down
            if (initialPuzzleForTracking[i + 1][j] == num) {
                System.out.println("found value " + num + " in " + i + 1 + " / " + j + " being on " + i + " / " + j);
                counter--;
            }
        }
    }

    private void checkAndFitEmptySpaces(int startRow, int endRow) {
        Point emptyPoint = new Point(startRow, 0);
        while (isEmptyPointValid(emptyPoint)) {
            emptyPoint = findEmptyPointForFilling(emptyPoint, startRow, endRow);
            List<IntegerPair> emptyPoints = findEmptyGroupForPoint(emptyPoint);
            if (emptyPoints != null && emptyPoints.size() > 0) {
                fillEmptyGroups(emptyPoints);
            }
        }
        System.out.println("Finish filling");
    }

    private Point findEmptyPointForFilling(Point previousPoint, int startRow, int endRow) {
        for (int i = previousPoint.x; i < endRow; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (i == previousPoint.x && j == previousPoint.y) continue;
                if (puzzle[i][j] == 0) {
                    return new Point(i, j);
                }
            }
        }
        return new Point(-1, -1);
    }

    private boolean isEmptyPointValid(Point point) {
        return point.x != -1 && point.y != -1;
    }

    private List<IntegerPair> findEmptyGroupForPoint(Point point) {
        List<IntegerPair> pairs = new ArrayList<>();
        dfsForEmpties(point.x, point.y, -1, pairs);
        System.out.println(pairs);
        return pairs;
    }

    private void dfsForEmpties(int r, int c, int direction, List<IntegerPair> pairs) {
        if (!checkCoordinates(r, c)) return;
        pairs.add(new IntegerPair(r, c));
        for (int i = 0; i < 4; i++) {
            if (!checkDirection(direction, i)) continue;
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >= 0 && nr < rowSize && nc >= 0 && nc < colSize && puzzle[nr][nc] == 0) {
                dfsForEmpties(nr, nc, i, pairs);
            }
        }
    }

    private boolean checkCoordinates(int r, int c) {
        return !(r < 0 || r >= rowSize || c < 0 || c >= colSize || visited[r][c]);
    }

    private void fillEmptyGroups(List<IntegerPair> pairs) {
        if (pairs.size() == 1) {
            IntegerPair integerPair = pairs.get(0);
            puzzle[integerPair.getX()][integerPair.getY()] = 1;
        }
        pairs.forEach(pair -> puzzle[pair.getX()][pair.getY()] = pairs.size());
    }

    private void printSolution() {
        System.out.println("Solution:");
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                System.out.print((visited[r][c] ? "X" : " ") + " ");
            }
            System.out.println();
        }
    }

    private void printPuzzle() {
        System.out.println("Current puzzle is ");
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                System.out.print(puzzle[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println("\n\n\n");
    }

    private void copyArray(int[][] initialArray, int[][] arrayForCopy) {
        for (int i = 0; i < initialArray.length; i++) {
            for (int j = 0; j < initialArray[0].length; j++) {
                arrayForCopy[i][j] = initialArray[i][j];
            }
        }
    }
}

