package com.github.amitsoni.jwt.jwtexample.security.module;

import com.github.amitsoni.jwt.jwtexample.security.constant.SecurityConstants;
import com.github.amitsoni.jwt.jwtexample.security.module.EmployeeDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Data
public class JwtTokenProvider {

    private byte[] jwtSecret;
    private Long jwtExpirationInMs;
    private String emailId;

    @Autowired
    public JwtTokenProvider(Environment environment) {
        this.jwtSecret = SecurityConstants.JWT_SECRET.getBytes();
        this.jwtExpirationInMs = 5000000L;
    }

    public String generateToken(Authentication authentication, List<String> roles) {

        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(employeeDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret), SignatureAlgorithm.HS512)
                .claim("rol", roles)
                .compact();
    }

    public String getUserFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        emailId = claims.getSubject();

        return emailId;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}