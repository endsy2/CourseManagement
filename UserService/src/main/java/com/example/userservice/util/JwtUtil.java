package com.example.userservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.private.key}")
    private String privateKeyStr;

    @Value("${jwt.public.key}")
    private String publicKeyStr;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private static final long ACCESSTOKENEXPIRATION = 1000 * 60 * 60; // 1 hour
    private static final long REFRESHTOKENEXPIRATION = 1000 * 60 * 60 * 24;

    @PostConstruct
    public void initKeys() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] privateBytes = Base64.getDecoder().decode(privateKeyStr);
        byte[] publicBytes = Base64.getDecoder().decode(publicKeyStr);

        privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
        publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));
    }

    // token generation
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESSTOKENEXPIRATION))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
    public String generateRefreshToken(String username,String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESHTOKENEXPIRATION))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }



    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
