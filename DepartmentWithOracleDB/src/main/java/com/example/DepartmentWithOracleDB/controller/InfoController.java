package com.example.DepartmentWithOracleDB.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${department.info}")
    private  String infoMessage;

    @GetMapping("/info")
    public String infoAboutThisApp(){
        return infoMessage;
    }
}
