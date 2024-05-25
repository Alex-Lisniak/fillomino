package com.example.programming_engineering_lab2.service;

import com.example.programming_engineering_lab2.model.AuthKey;
import com.example.programming_engineering_lab2.repository.AuthKeyRepository;
import com.example.programming_engineering_lab2.service.impl.AuthKeyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthKeyServiceImplTest {
    private final Long testLong = 470470L;
    @InjectMocks
    private AuthKeyServiceImpl authKeyService;
    @Mock
    private AuthKeyRepository authKeyRepository;
    @Test
    public void getKeyTest() {
        List<AuthKey> keys = new ArrayList<>();
        AuthKey authKey = new AuthKey();
        authKey.setExpTime(testLong);
        keys.add(authKey);
        when(authKeyRepository.findAll()).thenReturn(keys);

        AuthKey actualKey = authKeyService.getKey();
        assertEquals(testLong, actualKey.getExpTime());
    }
}
