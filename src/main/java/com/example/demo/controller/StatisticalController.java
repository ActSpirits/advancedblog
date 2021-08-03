package com.example.demo.controller;

import com.example.demo.bean.Statistical;
import com.example.demo.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/statistical")
public class StatisticalController {

    @Autowired
    private StatisticalService statisticalService;


    @RequestMapping("/get")
    @CrossOrigin
    public Statistical get() {
        return statisticalService.get();
    }
}
