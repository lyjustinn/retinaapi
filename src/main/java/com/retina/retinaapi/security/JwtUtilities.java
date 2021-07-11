package com.retina.retinaapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtilities {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String extractUsername(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return jws.getBody().getSubject();
        } catch (JwtException ex) {
            return null;
        }
    }

    public Date extractExpiryDate(String token) throws JwtException {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return jws.getBody().getExpiration();
        } catch (JwtException ex) {
            throw new JwtException("Could not parse expiry date", ex);
        }
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    public Boolean isValid(String token, UserDetails userDetails) {
        if (userDetails == null) return false;

        try {
            final String username = this.extractUsername(token);
            return username.equals(userDetails.getUsername()) && !this.isExpired(token);
        } catch (JwtException ex) {
            return false;
        }
    }

    private String createToken(String subject) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)))
                .signWith(key).compact();
    }

    private Boolean isExpired(String token) {
        try {
            Date expiryDate = this.extractExpiryDate(token);
            return expiryDate.before(new Date());
        } catch (JwtException ex) {
            return true;
        }
    }

}
