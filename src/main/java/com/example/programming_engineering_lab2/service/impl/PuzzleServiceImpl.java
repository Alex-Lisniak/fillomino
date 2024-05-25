package com.example.programming_engineering_lab2.service.impl;

import com.example.programming_engineering_lab2.model.PuzzleModel;
import com.example.programming_engineering_lab2.repository.PuzzleRepository;
import com.example.programming_engineering_lab2.service.PuzzleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PuzzleServiceImpl implements PuzzleService {
    private final PuzzleRepository puzzleRepository;
    @Override
    public PuzzleModel getPuzzleById(UUID uuid) {
        return puzzleRepository.getPuzzleById(uuid).orElse(null);
    }

    @Override
    public void savePuzzle(String puzzle) {
        PuzzleModel puzzleModel = new PuzzleModel();
        puzzleModel.setPuzzleField(puzzle);
        puzzleRepository.save(puzzleModel);
    }
}
