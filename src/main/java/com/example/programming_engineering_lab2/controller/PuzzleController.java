package com.example.programming_engineering_lab2.controller;

import com.example.programming_engineering_lab2.exception.KeyIsNotValidException;
import com.example.programming_engineering_lab2.service.AuthKeyService;
import com.example.programming_engineering_lab2.service.PuzzleService;
import com.example.programming_engineering_lab2.util.FillominoSolver;
import com.example.programming_engineering_lab2.converter.ArrayToJsonConverter;
import com.example.programming_engineering_lab2.converter.JsonToArrayConverter;
import com.example.programming_engineering_lab2.exception.NoSolutionFoundException;
import com.example.programming_engineering_lab2.util.PuzzleGeneratorUtil;
import com.example.programming_engineering_lab2.validation.AuthKeyValidator;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/puzzle")
public class PuzzleController {
    private final Logger logger = LoggerFactory.getLogger("PuzzleController");
    private static int puzzle[][] =  {
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

    private final ArrayToJsonConverter arrayConverter;
    private final JsonToArrayConverter jsonConverter;
    private final PuzzleService puzzleService;
    private final AuthKeyService authKeyService;
    private final AuthKeyValidator authKeyValidator;

    @Operation(summary = "Method to get valid key from db")
    @GetMapping("/key/get-key")
    public UUID getKey() {
        return authKeyService.getKey().getId();
    }

    @Operation(summary = "Get example puzzle request", description = "Using that method you can get example of puzzle to provide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Internal error")
    })
    @GetMapping("/get-example-puzzle")
    public ResponseEntity getExamplePuzzle (@RequestHeader(value = "key") UUID key) {
        validateKey(key);
        return new ResponseEntity<>(arrayConverter.convertArrayToJson(puzzle).toString(), HttpStatus.OK);
    }

    @Operation(summary = "Solve puzzle by given array", description = "This method returns solution of given puzzle")
    @PostMapping("/solve-puzzle")
    public ResponseEntity solvePuzzle (@RequestHeader(value = "key") UUID key,@RequestParam JSONObject conditions) {
        validateKey(key);
        int columnSize = conditions.keySet().size();
        int rowSize = conditions.getJSONArray(Integer.toString(columnSize-1)).length();
        int arrayToSolve[][] = new int[columnSize][rowSize];
        jsonConverter.convertJsonToArray(conditions, arrayToSolve);
        FillominoSolver fillominoSolver = new FillominoSolver(arrayToSolve);
        try {
            fillominoSolver.solve();
        } catch (NoSolutionFoundException noSolutionFoundException) {
            logger.error(noSolutionFoundException.getMessage());
        }
        return new ResponseEntity<>(arrayConverter.convertArrayToJson(arrayToSolve).toString(), HttpStatus.OK);
    }
    @Operation(summary = "Get puzzle condition", description = "Using that method you can get a random puzzle condition to solve")
    @GetMapping("/puzzle-condition")
    public ResponseEntity getPuzzleCondition (@RequestHeader(value = "key") UUID key) {
        validateKey(key);
        int size = PuzzleGeneratorUtil.defaultSize;
        int array [][] = new int[size][size];
        PuzzleGeneratorUtil.generatePuzzle(array);
        JSONObject puzzle = arrayConverter.convertArrayToJson(array);
        puzzleService.savePuzzle(puzzle.toString());
        return new ResponseEntity<>(puzzle.toString(), HttpStatus.OK);
    }
    @Operation(summary = "Get puzzle by id", description = "Using that method you can get puzzle by id")
    @GetMapping("/get-puzzle-by-id/{puzzleId}")
    public ResponseEntity getPuzzleById (@RequestHeader(value = "key") UUID key, @PathVariable String puzzleId) {
        validateKey(key);
        return new ResponseEntity<>(puzzleService.getPuzzleById(UUID.fromString(puzzleId)), HttpStatus.OK);
    }
    private boolean validateKey(final UUID key){
        if (authKeyValidator.validate(key)) {
            return true;
        } else throw new KeyIsNotValidException("Key is not valid " +  new Date().getTime() );
    }
}
