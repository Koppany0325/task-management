package com.example.taskmanagement.security;

import com.example.taskmanagement.users.entities.Role;
import com.example.taskmanagement.users.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTGenerator {

    private final UserRepository userRepository;

    @Value("${security.jwt-secret:}")
    private String JWT_SECRET;

    @Value("${security.jwt-expiration:}")
    private long JWT_EXPIRATION;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        List<Role> roles = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found")).getRoles();

        var rolesSet = new HashSet<String>();
        roles.forEach(role -> {
            rolesSet.add(role.getName());
        });

        String token = Jwts.builder()
            .setSubject(username)
            .claim("roles", rolesSet)
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
            .compact();
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }


    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT expired or incorrect");
        }
    }
}
