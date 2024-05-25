package com.example.programming_engineering_lab2.repository;

import com.example.programming_engineering_lab2.model.AuthKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface AuthKeyRepository extends CrudRepository<AuthKey, UUID> {
    @Override
    Iterable<AuthKey> findAll();
}

