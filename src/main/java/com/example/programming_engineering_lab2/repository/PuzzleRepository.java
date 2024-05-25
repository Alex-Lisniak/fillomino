package com.example.programming_engineering_lab2.repository;

import com.example.programming_engineering_lab2.model.PuzzleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PuzzleRepository  extends CrudRepository<PuzzleModel, UUID> {
    Optional<PuzzleModel> getPuzzleById(UUID id);
}
