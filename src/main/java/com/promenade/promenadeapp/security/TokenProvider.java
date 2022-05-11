package com.promenade.promenadeapp.security;

import com.promenade.promenadeapp.domain.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("{jwt-secret-key}")
    private String SECRET_KEY;

    public String createToken(User user) {
        // 기한은 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        // JWT Token 생성
        return Jwts.builder()
                // header
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload
                .setSubject(user.getGoogleId()) // sub
                .setIssuer("WalkHolic") // iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
