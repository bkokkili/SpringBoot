package com.store.seafoodveggies.service;

import com.store.seafoodveggies.dto.LoginDto;
import com.store.seafoodveggies.dto.LoginResponseDto;
import com.store.seafoodveggies.dto.RefreshTokenResponse;
import com.store.seafoodveggies.entity.User;

public interface LoginService {
    LoginResponseDto loginUser(LoginDto loginDto);
    RefreshTokenResponse refreshAccessToken(String refreshToken);
}
