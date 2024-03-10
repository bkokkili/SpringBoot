package com.springdemo.firstspringbootapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

@RequestMapping("/hello")
    public String hello(){
        return "Welcome to SpringBoot";
    }
    @RequestMapping("/test")
    public String test(){
    return "test";
    }

}
