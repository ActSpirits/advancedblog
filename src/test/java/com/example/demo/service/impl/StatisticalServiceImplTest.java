package com.example.demo.service.impl;

import com.example.demo.service.StatisticalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatisticalServiceImplTest {

    @Autowired
    private StatisticalService statisticalService;

    @Test
    void get() {
        System.out.println(statisticalService.get());
    }
}