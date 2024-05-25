package com.example.programming_engineering_lab2.validation;

import com.example.programming_engineering_lab2.exception.KeyIsNotValidException;
import com.example.programming_engineering_lab2.model.AuthKey;
import com.example.programming_engineering_lab2.repository.AuthKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthKeyValidator {

    private final AuthKeyRepository authKeyRepository;

    public boolean validate(UUID keyId){
        AuthKey authKey = authKeyRepository.findById(keyId).orElseThrow( () -> new KeyIsNotValidException("No key with that id found!! "));
        return authKey.getExpTime() > new Date().getTime();
    }
}
