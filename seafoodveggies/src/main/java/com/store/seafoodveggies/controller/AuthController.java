package com.store.seafoodveggies.controller;

import com.store.seafoodveggies.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return null;
    }
}
