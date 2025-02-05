package org.example.startupprjoect.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.example.startupprjoect.model.UserE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTGenrator {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication, UserE user) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        System.out.println("Generating token for user: " + username + ", ID: " + user.getId());

        var roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", user.getId())  // Make sure user ID is included
                .claim("email", username)       // Include email
                .claim("roles", roles)          // Include roles
                .claim("name", user.getName())  // Include name
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey())
                .compact();
    }
    public String getUsernameFromJwt(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public Long extractUserId(String token) {
        Claims claims = extractClaims(token);
        return claims.get("userId", Long.class);
    }

    public List<String> getRolesFromJwt(String token) {
        Claims claims = extractClaims(token);
        return claims.get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT signature or malformed token");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT claims string is empty");
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token: " + e.getMessage());
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}



