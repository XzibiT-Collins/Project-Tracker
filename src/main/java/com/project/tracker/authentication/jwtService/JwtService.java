package com.project.tracker.authentication.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        SecretKey secreteKey = jwtConfig.getSecreteKey(); // retrieve secret key from config
        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(System.currentTimeMillis() + (60 * 60 * 1000 * 15)); //15 mins validation period

        //return generated token
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(now)
                .expiration(exp)
                .and()
                .signWith(secreteKey) //key generation
                .compact();
    }

    public String extractUserName(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
