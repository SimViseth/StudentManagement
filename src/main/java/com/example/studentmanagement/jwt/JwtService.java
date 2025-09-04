package com.example.studentmanagement.jwt;

import com.example.studentmanagement.model.entity.mysql.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtService {
    private final static long JWT_TOKEN_TIME = 5 * 60 * 60; // 5h
    private final static String SECRET = "5465464bcd3967c1859c1c9eeb365dc8ebd62e782dbfa7e094b6e40404dcdb8b15f4bcd3967c1859c1c9eeb365dc8ebd62e782dbfa7e094b6e40404dcdb8b15f";

    // define for other method call to use and pass it back to this method
    private String createToken(Map<String, Object> claim, String subject){
        return Jwts.builder()
                .setClaims(claim) // empty claim
                .setSubject(subject) // can be id, username or email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_TIME * 1000)) // 1s = 1000 mili second
                .signWith(getSignKey() , SignatureAlgorithm.HS256).compact(); // check getSignKey() equal SignatureAlgorithm.HS256 or not
    }

    public Key getSignKey(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes); // hash for signature
    }

    // generate token for user
    public String generateToken(User appUser){
        Map<String, Object> claims = new HashMap<>();
        // store the userId in the token’s payload
        claims.put("userId", appUser.getUserId()); // custom claim
        return createToken(claims, appUser.getEmail()); // subject - standard
    }

    // retrieving any information from token we will need the secret key
    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extract a specific claim from the JWT token's claims ex below: username,
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    // retrieve username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // retrieve expiration date from JWT token
    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // check expired token
    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    // validate token: check the user from token and user detail are the same or not
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
