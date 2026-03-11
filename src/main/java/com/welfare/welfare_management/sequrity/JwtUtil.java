package com.welfare.welfare_management.sequrity;

import com.welfare.welfare_management.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

//@Component
//public class JwtUtil {
//
//    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey"; // Use at least 32 chars
//    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
//
//
//    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .subject(username) // Use subject() instead of setSubject()
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(getSigningKey(), Jwts.SIG.HS256) // Updated signature algorithm
//                .compact();
//    }
//
//
//    public String extractUsername(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    public boolean validateToken(String token, String username) {
//        return username.equals(extractUsername(token)) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return getClaims(token).getExpiration().before(new Date());
//    }
//
//    private Claims getClaims(String token) {
//        return Jwts.parser() // Use parser() for newer versions
//                .verifyWith(getSigningKey()) // Use verifyWith() instead of setSigningKey()
//                .build()
//                .parseSignedClaims(token) // Use parseSignedClaims() instead of parseClaimsJws()
//                .getPayload(); // Use getPayload() instead of getBody()
//    }
//
//
///////////////////////////////log out/////
//    // ✅ Extract expiration
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    // ✅ Generic claim extractor
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//
//
//
//
//
//}

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey"; // 32+ chars
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24; // 1 day // 1440 minutes
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /* ==========================================================
      TOKEN GENERATION
       ========================================================== */

    // Generate Access Token (short-lived)
//    public String generateAccessToken(String username) {
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
//                .signWith(getSigningKey(), Jwts.SIG.HS256)
//                .compact();
//    }
    public String generateAccessToken(String username, Role role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    // Generate Refresh Token (longer-lived)
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /* ==========================================================
       🟡 TOKEN VALIDATION
       ========================================================== */

    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (username.equals(extractedUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token); // will throw if invalid
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /* ==========================================================
       🔵 CLAIMS EXTRACTION
       ========================================================== */

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}