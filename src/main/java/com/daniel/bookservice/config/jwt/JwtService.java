package com.daniel.bookservice.config.jwt;

import com.daniel.bookservice.model.User;
import com.daniel.bookservice.model.enums.Roles;
import com.daniel.bookservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "b3e7f2772bb641e3fd272ca23f268510e9f8c24395984d460487d7f578b9281";
    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Key getSigningKey(){
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    private <T>T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private String generateToken(Map<String, Object> getDetails, UserDetails userDetails){
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if(!user.isPresent()){
            return "error";
        }
        Roles role = user.get().getRole();
        getDetails.put("role", role.toString());
        return Jwts.builder()
                .setClaims(getDetails)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Date extractTokenCreation(String token){
        return extractClaim(token, Claims::getIssuedAt);
    }
    public Roles extractRole(String token){
        Claims claims = extractAllClaims(token);
        String roleString =  (String) claims.get("role");
        return Roles.valueOf(roleString);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
