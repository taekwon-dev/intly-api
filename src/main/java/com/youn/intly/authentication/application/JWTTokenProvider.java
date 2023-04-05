package com.youn.intly.authentication.application;

import com.youn.intly.exception.authentication.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    private final String secretKey;
    private final long expirationInMillis;

    public JWTTokenProvider(
        @Value("${security.jwt.secret-key}") String secretKey,
        @Value("${security.jwt.expiration}") long expirationInMillis
    ) {
        this.secretKey = secretKey;
        this.expirationInMillis = expirationInMillis;
    }

    public String createToken(final String payload) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationInMillis);

        return Jwts.builder()
                .claim("username", payload)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public String getPayloadByKey(final String token, final String key) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(key, String.class);
        } catch (JwtException | IllegalArgumentException e) {
             throw new InvalidTokenException();
        }
    }
}