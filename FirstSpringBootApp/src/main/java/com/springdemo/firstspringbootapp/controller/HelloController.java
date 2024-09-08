package com.springdemo.firstspringbootapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to the SpringBoot Application";
    }

    @RequestMapping("/test")
    public String test() {
        return "test successful";
    }

}