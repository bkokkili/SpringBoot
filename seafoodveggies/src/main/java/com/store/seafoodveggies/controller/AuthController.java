package com.store.seafoodveggies.controller;

import com.store.seafoodveggies.dto.LoginDto;
import com.store.seafoodveggies.dto.LoginResponseDto;
import com.store.seafoodveggies.dto.RefreshTokenResponse;
import com.store.seafoodveggies.entity.User;
import com.store.seafoodveggies.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto){
        return loginService.loginUser(loginDto);
    }

    public RefreshTokenResponse refreshToken(@PathVariable String refreshToken){
        return loginService.refreshAccessToken(refreshToken);
    }

}
