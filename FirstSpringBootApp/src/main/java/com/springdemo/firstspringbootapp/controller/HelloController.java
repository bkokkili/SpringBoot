package com.springdemo.firstspringbootapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public  class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Welcome to SpringBoot";
    }

    @RequestMapping("/test")
    public String test() {
        return "test successful";
    }

}