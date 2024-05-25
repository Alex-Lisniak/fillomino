package com.example.programming_engineering_lab2.service;

import com.example.programming_engineering_lab2.model.PuzzleModel;

import java.util.UUID;

public interface PuzzleService {
    PuzzleModel getPuzzleById(UUID uuid);
    void savePuzzle(String puzzle);
}
