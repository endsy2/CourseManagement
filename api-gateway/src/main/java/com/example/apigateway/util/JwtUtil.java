package com.example.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String JWT_SECRET = "kSoolXL5fFhAGbtSgdbKx9u8OCvN90xDQszy9mwODk=";
    private static final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    public static Claims validateToken (String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
    }
    public static boolean isTokenExpired(String token){
        Date expiration = validateToken(token).getExpiration();
        return expiration.before(new Date());
    }
}
