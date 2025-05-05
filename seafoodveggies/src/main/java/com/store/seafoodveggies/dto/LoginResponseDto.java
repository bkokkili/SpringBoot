package com.store.seafoodveggies.dto;

import com.store.seafoodveggies.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private User user;

    private String accessToken;

    private String refreshToken;

}
