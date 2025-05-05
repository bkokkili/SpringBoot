package com.store.seafoodveggies.security;

import com.store.seafoodveggies.entity.User;
import com.store.seafoodveggies.exception.InvalidTokenException;
import com.store.seafoodveggies.exception.TokenGenerationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final static Logger LOG = LoggerFactory.getLogger(JwtService.class);

    public String generateToken(User user) {

        if (user == null || user.getUserName() == null) {
            throw new InvalidTokenException("Cannot generate access token: User or username is null.");
        }

        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            //claims.put("role", user.getRole());
            claims.put("email", user.getEmail());

            return Jwts
                    .builder()
                    .claims()
                    .add(claims)
                    .subject(user.getUserName())
                    .issuer("Balaji")
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+ 15*60*1000))
                    .and()
                    .signWith(generateKey())
                    .compact();
        }catch (Exception e){
            throw new TokenGenerationException("Error generating access token: " + e.getMessage());
        }

    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey() {
        return "+FOGJVDeRdAlGZs9LKHt1AmJhsqfxdfeYZm9dRNZe+g=";
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String refreshToken(User user) {
        if (user == null || user.getUserName() == null) {
            throw new InvalidTokenException("Cannot generate refresh token: User or username is null.");
        }

        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("purpose", "refresh_AccessToken");

            return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(user.getUserName())
                    .issuer("Balaji")
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
                    .and()
                    .signWith(generateKey())
                    .compact();

        } catch (Exception e) {
            throw new TokenGenerationException("Error generating refresh token: " + e.getMessage());
        }
    }

    public boolean isRefreshToken(String refreshToken){
        Claims  claims = extractClaims(refreshToken);
        String purpose = (String) claims.get("purpose");
        return "refresh_AccessToken".equals(purpose);
    }
}
