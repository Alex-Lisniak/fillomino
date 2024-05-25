package com.example.programming_engineering_lab2.service.impl;

import com.example.programming_engineering_lab2.model.AuthKey;
import com.example.programming_engineering_lab2.repository.AuthKeyRepository;
import com.example.programming_engineering_lab2.service.AuthKeyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthKeyServiceImpl implements AuthKeyService {

    private final AuthKeyRepository keyRepository;

    @Override
    public AuthKey getKey() {
        return keyRepository.findAll().iterator().next();
    }

}
