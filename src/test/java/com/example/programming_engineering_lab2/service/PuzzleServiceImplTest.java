package com.example.programming_engineering_lab2.service;

import com.example.programming_engineering_lab2.model.PuzzleModel;
import com.example.programming_engineering_lab2.repository.PuzzleRepository;
import com.example.programming_engineering_lab2.service.impl.PuzzleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PuzzleServiceImplTest {
    private final UUID testUUID = UUID.fromString("4cbb2a7b-be0a-456e-b5b8-c951d05c4119");
    @InjectMocks
    private PuzzleServiceImpl puzzleService;
    @Mock
    private PuzzleRepository puzzleRepository;
    @Mock
    private PuzzleModel puzzle;
    @Before
    public void setup() {
        when(puzzle.getId()).thenReturn(testUUID);
    }
    @Test
    public void getPuzzleByIdTest() {
        when(puzzleRepository.getPuzzleById(testUUID)).thenReturn(Optional.of(puzzle));
        UUID actual = puzzleService.getPuzzleById(testUUID).getId();
        assertEquals(actual, testUUID);
    }
    @Test
    public void savePuzzleTest() {
        puzzleService.savePuzzle("testPuzzle");
        verify(puzzleRepository, times(1)).save(any());
    }
}
