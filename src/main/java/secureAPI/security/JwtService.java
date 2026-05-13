package secureAPI.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    // ── Generate Token ──────────────────────────────────────────
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ── Extract Email ───────────────────────────────────────────
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ── Validate Token ──────────────────────────────────────────
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ── Check Expiry ────────────────────────────────────────────
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // ── Parse All Claims  ───────────────────────────────────────
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()       // <-- parserBuilder() not parser()
                .setSigningKey(getSigningKey())  // <-- setSigningKey() not verifyWith()
                .build()
                .parseClaimsJws(token)   // <-- parseClaimsJws() not parseSignedClaims()
                .getBody();              // <-- getBody() not getPayload()
    }

    // ── Signing Key ─────────────────────────────────────────────
    private SecretKey getSigningKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}