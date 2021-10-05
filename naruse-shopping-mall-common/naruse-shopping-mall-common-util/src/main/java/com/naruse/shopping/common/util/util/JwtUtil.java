package com.naruse.shopping.common.util.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {

    private static final String tokenKey = "Naruse Shinji";

    public static String createToken(String subject) {
        String token = Jwts.builder().setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, tokenKey)
                .compact();
        return token;
    }

    public static String parseToken(String token) {
        Claims body = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}
